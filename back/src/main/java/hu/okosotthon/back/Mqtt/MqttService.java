package hu.okosotthon.back.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.okosotthon.back.Device.*;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Sensor.SensorService;
import hu.okosotthon.back.IfThen.IfThen;
import hu.okosotthon.back.IfThen.IfThenService;
import hu.okosotthon.back.Measurment.Measurement;
import hu.okosotthon.back.Measurment.MeasurementService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MqttService {

    public static DetectedDevice detectedDevice = new DetectedDevice();
    private boolean autoDetection = false;


    private DeviceService deviceService;
    private IfThenService ifThenService;
    private SensorService sensorService;
    private MeasurementService measurementService;

    @Autowired
    public MqttService(
            DeviceService deviceService,
            IfThenService ifThenService,
            SensorService sensorService,
            MeasurementService measurementService) {
        this.deviceService = deviceService;
        this.ifThenService = ifThenService;
        this.sensorService = sensorService;
        this.measurementService = measurementService;
    }

    private WebSocModel webSocModel;

    String broker = "tcp://192.168.0.28:1883";
    //A ClientId az lehetne aki ben van jelentkezve
    String clientId = "SpringBootSubscriber";
    MqttClient mqttClient;

    String topicSearch = "";

    public void connectToBroker() {
        try {
            mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            messaging();
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void setAutoDetection(boolean val){
        this.autoDetection = val;
    }

    public void setWebSocModel(WebSocModel webSocModel) {
        this.webSocModel = webSocModel;
    }

    public void subscribeToTopic(WebSocModel webSocModel) {
        try {
            for (MessageModel m : webSocModel.getMessage()) {
                String tmpTopic = m.getPrefix()+webSocModel.getTopic()+m.getPostfix();
                mqttClient.subscribe(tmpTopic);


                if(m.getPrefix().contains("cmnd")){
                    System.out.println(tmpTopic+" sndlfkjasnlkdf");
                    mqttClient.publish(tmpTopic, new MqttMessage(m.getMsg().getBytes()));
                }
                tmpTopic = "stat/"+webSocModel.getTopic()+"/RESULT";
                System.out.println(tmpTopic);
                mqttClient.subscribe(tmpTopic);
            }
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendMessageToDevice(WebSocModel webSocModel) {
        try {
            for (MessageModel m : webSocModel.getMessage()) {
                String tmpTopic = m.getPrefix()+webSocModel.getTopic()+m.getPostfix();
                mqttClient.publish(tmpTopic, new MqttMessage(m.getMsg().getBytes()));
            }
            return true;
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    public void messaging() {
        mqttClient.setCallback(new MqttCallback() {

            @Override
            @Async
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
                System.out.println(message+"  Messasge arrived");
                if (msg.length() > 2) {
                    try {
                        System.out.println(msg);
                        new JSONObject(msg);

                        if (!autoDetection) {
                            System.out.println("Listen  " + webSocModel.getListen());
                            messagingTemplate.convertAndSend(webSocModel.getListen(), message.toString());
                        }

                        if (topic.startsWith("stat/") && autoDetection) {
                            STATMapper(msg, topic);
                        }

                        if (topic.startsWith("tele/") || topic.endsWith("/SENSOR")) {
                            TELEMapper(msg, topic);
                        }


                        if (!detectedDevice.getGpio().isEmpty()) {
                            if (detectedDevice.getDeviceName().isEmpty()) {
                                detectedDevice.setDeviceName("Null");
                            }
                            System.out.println("device is not null");
                            messagingTemplate.convertAndSend(webSocModel.getListen(), detectedDevice);
                            System.out.println("Device is being cleard");
                            System.out.println(detectedDevice.toString());
                            detectedDevice = new DetectedDevice();
                            webSocModel = new WebSocModel();
                            autoDetection = false;
                        }


                    } catch (JSONException e) {
                        System.err.println(e);
                    }
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost" + cause);
                connectToBroker();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Not used in this example
            }

        });
    }

    public String topicCutter(String topic) {
        List<String> partsToCut = Arrays.asList("stat/", "/POWER", "/RESULT", "/SENSOR");
        for (String part : partsToCut) {
            topic.replace(part, "");
        }
        return topic;
    }

    public void TELEMapper(String jsonObject, String topic) {
        List<Sensor> sensorList = this.sensorService.getAll();
        String tmp = topic.replace("tele/", "").replace("/SENSOR", "");
        Devices d = this.deviceService.getDeviceByTopic(tmp);
        System.out.println("TELEMapper " + jsonObject);
        try {
            HashMap<Object, Object> result = new ObjectMapper().readValue(jsonObject, HashMap.class);
            for (Map.Entry<Object, Object> val : result.entrySet()) {
//                System.out.println(val.getKey() + " " + val.getValue());
                for (Sensor s : sensorList) {
                    //TODO ki kell próbálni a Measurment savet
                    if (s.getSensorName().equals(val.getKey())) {
                        System.out.println("GSON " + val.getValue());
//                        String canonicalFormat = JsonParser.parseString(val.getValue().toString()).toString();
//                        HashMap<Object, Object> sensor = new ObjectMapper().readValue(canonicalFormat, HashMap.class);
                        LinkedHashMap<Object, Object> sensor = (LinkedHashMap<Object, Object>) val.getValue();
                        for (Map.Entry<Object, Object> senVal : sensor.entrySet()) {
                            this.measurementService.save(new Measurement(senVal.getKey().toString(), senVal.getValue().toString(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), d));

                        }


                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    private boolean noStatusSNS = false;

    public void STATMapper(String jsonObject, String topic) {
        try {
            new JSONObject(jsonObject);
            HashMap<Object, Object> result = new ObjectMapper().readValue(jsonObject, HashMap.class);
            for (Map.Entry<Object, Object> val : result.entrySet()) {

                if (val.getValue() instanceof LinkedHashMap) {
                    Map<Object, Object> map = (Map<Object, Object>) val.getValue();

                    for (Map.Entry<Object, Object> mapVal : map.entrySet()) {
                        if (val.getKey().equals("Status") && mapVal.getKey().equals("DeviceName")) {
                            detectedDevice.setDeviceName((String) mapVal.getValue());
                        }
                        if (val.getKey().equals("StatusSNS")) {
                            if (mapVal.getValue() instanceof LinkedHashMap) {
                                LinkedHashMap<Object, Object> stat = (LinkedHashMap<Object, Object>) mapVal.getValue();
                                List<Object> keys = new ArrayList<>();
                                for (Map.Entry<Object, Object> tmp : stat.entrySet()) {
                                    keys.add(tmp.getKey());
                                }
                                detectedDevice.setStatusSNS(mapVal.getKey().toString());
                            }else {
                                noStatusSNS = true;
                            }
                        }
                        if (val.getKey().toString().contains("GPIO") && mapVal.getKey() != "0" && mapVal.getValue() != "None") {
                            if (noStatusSNS && detectedDevice.getStatusSNS().isEmpty() && mapVal.getValue().toString().contains("PWM") ) {
                                detectedDevice.setStatusSNS(pwmGPIO(result));
                            }
                            detectedDevice.setGpio(
                                    new GPIO(
                                            val.getKey().toString(),
                                            mapVal.getKey().toString(),
                                            mapVal.getValue().toString()));
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String pwmGPIO(Map<Object, Object> map) {
        int max = 0;
        for (Map.Entry<Object, Object> val : map.entrySet()) {
            Map<Object, Object> m = (Map<Object, Object>) val.getValue();
            for (Map.Entry<Object, Object> mapVal : m.entrySet()) {
                if (mapVal.getValue() != "None" && mapVal.getValue().toString().contains("PWM")) {
                    if (Integer.parseInt(String.valueOf(mapVal.getValue().toString().charAt(3))) > max) {
                        max = Integer.parseInt(String.valueOf(mapVal.getValue().toString().charAt(3)));
                    }
                }
            }
        }
        return "PWM" + max;
    }


    @Async
    public void handleAsyncMessage(String topic, String payload) {
        // This method is called asynchronously to handle the received message
        // Perform operations like saving data to the database or other time-consuming tasks
        System.out.println("Handling message asynchronously...");
        System.out.println("Topic: " + topic);
        System.out.println("Payload: " + payload);
        // Implement your database interaction logic here
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void checkForActions() {

        //TODO itt majd úgy kell lekérdezni az autómatizációt hogy az adott időre szűrünk és akkor kevesebb soron kell végig menni
        List<IfThen> ifThenList = this.ifThenService.getAllIfThen();

        ifThenList.forEach(ifThen -> {
            //TODO itt majd azt kell hogy amikor lefut akkor nézze meg hány óra van és ha matchel valamivel akkor küldjön egy üzenetett
            if (ifThen.getWhenToCheck().equals("10:10")) {
//                publishDataToDevice();
                System.out.println(ifThen.getMessage());
            }
        });
    }
}


/**
 * Az automatizációt úgy lehetne megoldani hogy amikor a backend megkapja az üzenetett akkor az eszköz ID alapján lekérje
 * ahhoz az eszközhöz kapcsolódi ifthen-eket és miután lekérdezte akkor ellenőrizze hogy teljesül e valamelyikre,
 * ha igen akkor hajtsa végre az autómatizációt
 */