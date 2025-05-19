package hu.okosotthon.back.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.okosotthon.back.Device.*;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Sensor.SensorService;
import hu.okosotthon.back.scheduleTask.Frequency;
import hu.okosotthon.back.scheduleTask.ScheduleTask;
import hu.okosotthon.back.scheduleTask.ScheduleTaskDTO;
import hu.okosotthon.back.scheduleTask.ScheduleTaskService;
import hu.okosotthon.back.Measurment.Measurement;
import hu.okosotthon.back.Measurment.MeasurementService;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommandService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
public class MqttService {

    public static DetectedDevice detectedDevice = new DetectedDevice();
    private boolean autoDetection = false;
    private boolean isStatusCheck = false;

    private List<WebSocModel> webSocModel = new ArrayList<>();

    List<ScheduleTaskDTO> scheduleTask = new ArrayList<>();


    private DeviceService deviceService;
    private ScheduleTaskService scheduleTaskService;
    private SensorService sensorService;
    private MeasurementService measurementService;
    private TasmotaCommandService tasmotaCommandService;

    @Autowired
    public MqttService(
            DeviceService deviceService,
            ScheduleTaskService scheduleTaskService,
            SensorService sensorService,
            MeasurementService measurementService,
            TasmotaCommandService tasmotaCommandService) {
        this.deviceService = deviceService;
        this.scheduleTaskService = scheduleTaskService;
        this.sensorService = sensorService;
        this.measurementService = measurementService;
        this.tasmotaCommandService = tasmotaCommandService;
    }


    String broker = "tcp://192.168.0.28:1883";
    //A ClientId az lehetne aki ben van jelentkezve
    String clientId = "SpringBootSubscriber";
    MqttClient mqttClient;


    public void connectToBroker() {
        try {
            mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("admin");
            connOpts.setPassword(("admin").toCharArray());
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

    public void setAutoDetection(boolean val) {
        this.autoDetection = val;
    }

    public void setStatusCheck(boolean val) {
        this.isStatusCheck = val;
    }

    public void setWebSocModel(WebSocModel webSocModel) {
        this.webSocModel.add(webSocModel);
    }

    public void subscribeToTopic(WebSocModel webSocModel) {
        System.out.println("Subscribing to websocket and topic");
        try {
            for (MessageModel m : webSocModel.getMessage()) {
                String tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
                mqttClient.subscribe(tmpTopic);


                if (m.getPrefix().contains("cmnd")) {
                    tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
                    mqttClient.publish(tmpTopic, new MqttMessage(m.getMsg().getBytes()));
                }

                if (m.getPrefix().contains("stat")) {
                    tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
                    System.out.println(tmpTopic);
                    mqttClient.subscribe(tmpTopic);
                }

            }
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribeToTopic2(String topic) {
        try {
            mqttClient.subscribe("tele/" + topic + "/SENSOR");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendMessageToDevice(WebSocModel webSocModel) {
        try {
            for (MessageModel m : webSocModel.getMessage()) {
                String tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
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
                System.out.println(msg + "  Messasge arrived");
                if (msg.length() > 2) {
                    try {
                        System.out.println(msg);
                        new JSONObject(msg);

                        if (isStatusCheck) {
                            statusCheck(topic, message.toString());
                        }

                        if (topic.startsWith("stat/") && autoDetection) {
                            STATMapper(msg, topic);
                        }

                        if (topic.startsWith("tele/") || topic.endsWith("/SENSOR")) {
                            TELEMapper(msg, topic);
                        }


                        if (!detectedDevice.getGpio().isEmpty()) {
                            sendDetectedDevice(topic);
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

    public void statusCheck(String topic, String msg) {
        for (WebSocModel ws : this.webSocModel) {
            if (topic.contains(ws.getTopic())) {
                messagingTemplate.convertAndSend(ws.getListen(), msg);
                this.webSocModel.remove(ws);
            }
        }
        setStatusCheck(false);
    }

    public void sendDetectedDevice(String topic) {
        if (detectedDevice.getDeviceName().isEmpty()) {
            detectedDevice.setDeviceName("Null");
        }
        System.out.println("device is not null");
        WebSocModel tmp = new WebSocModel();
        for (WebSocModel ws : this.webSocModel) {
            if (topic.contains(ws.getTopic())) {
                messagingTemplate.convertAndSend(ws.getListen(), detectedDevice);
                detectedDevice = new DetectedDevice();
                tmp = ws;
                break;
            }
        }
        this.webSocModel.remove(tmp);
        System.out.println(this.webSocModel.toString());
        System.out.println("Device is being cleard");
        System.out.println(detectedDevice.toString());
        setAutoDetection(false);
    }

    boolean automation = false;


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
                            if (automation) {
                                checkCondition(senVal.getKey().toString() + " " + senVal.getValue().toString());
                            }
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
                            } else {
                                noStatusSNS = true;
                            }
                        }
                        if (val.getKey().toString().contains("GPIO") && mapVal.getKey() != "0" && mapVal.getValue() != "None") {
                            if (noStatusSNS && detectedDevice.getStatusSNS().isEmpty() && mapVal.getValue().toString().contains("PWM")) {
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


    @Scheduled(cron = "0 * * * * *")
    public void checkForTimeActions() {
        //TODO scheduleing ot megcsinálni
        System.out.println("checkForActions");
        List<ScheduleTaskDTO> scheduleTask = this.scheduleTaskService.getAllScheduleForTime();
        if (!scheduleTask.isEmpty()) {
            scheduleTask.forEach(task -> {
                Devices d = this.deviceService.getDeviceById(task.getDevices_id());
                TasmotaCommand tc = this.tasmotaCommandService.getCommandById(task.getCommand_id());
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                if (task.getScheduledTime().equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))) {
                    if (task.getFrequency() == Frequency.DAILY) {
                        sendMessagetoDevice(d.getTopic(), tc.getCommand(), tc.getArgument());
                    } else if (task.getFrequency() == Frequency.WEEKLY) {
                        sendMessagetoDevice(d.getTopic(), tc.getCommand(), tc.getArgument());
                    } else if (task.getFrequency() == Frequency.MONTHLY) {
                        sendMessagetoDevice(d.getTopic(), tc.getCommand(), tc.getArgument());
                    }
                }
            });
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkForConditionActions() {
        automation = true;
        System.out.println("checkForConditionActions");
        scheduleTask = this.scheduleTaskService.getAllScheduleForCondition();
        if (!scheduleTask.isEmpty()) {
            scheduleTask.forEach(task -> {
                Devices d = this.deviceService.getDeviceById(task.getDevices_id());

                subscribeToTopic2(d.getTopic());
                sendMessagetoDevice(d.getTopic(), "STATUS", "8");

                unsubscribeFromTopic(d.getTopic(), "STATUS");
            });
        }
    }

    public void checkCondition(String status){

        this.scheduleTask.forEach(task -> {
            Devices targetDevice = this.deviceService.getDeviceById(task.getTargetDeviceId());

            TasmotaCommand tasmotaCommand = this.tasmotaCommandService.getCommandById(task.getCommand_id());
            String[] tmp = status.split(" ");
            System.out.println(tmp[0] + " " + tmp[1]);
            System.out.println(task.getCondition());
            switch (task.getCondition()) {
                case "lt":
                    if (tmp[0].equals(task.getWhichValue()) && Float.parseFloat(tmp[1]) < task.getWhen()) {
                        sendMessagetoDevice(targetDevice.getTopic(), tasmotaCommand.getCommand(), tasmotaCommand.getArgument());
                        System.out.println("LT message send");
                    }
                    break;
                case "gt":
                    if (tmp[0].equals(task.getWhichValue()) && Float.parseFloat(tmp[1]) > task.getWhen()) {
                        sendMessagetoDevice(targetDevice.getTopic(), tasmotaCommand.getCommand(), tasmotaCommand.getArgument());
                        System.out.println("GT message send");
                    }
                    break;
                case "eq":
                    if (tmp[0].equals(task.getWhichValue()) && Float.parseFloat(tmp[1]) == task.getWhen()) {
                        sendMessagetoDevice(targetDevice.getTopic(), tasmotaCommand.getCommand(), tasmotaCommand.getArgument());
                        System.out.println("EQ message send");
                    }
                    break;
            }
        });
        automation = false;

    }

    public void sendMessagetoDevice(String topic, String cmnd, String argument) {
        try {
            System.out.println("cmnd/" + topic + "/" + cmnd);
            this.mqttClient.publish("cmnd/" + topic + "/" + cmnd, new MqttMessage(argument.getBytes()));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void unsubscribeFromTopic(String topic, String cmnd) {
        try {
            this.mqttClient.unsubscribe("cmnd/"+ topic + "/"+cmnd);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

//    public void sendCommandToDevice(String topic,String cmnd, String msg){
//        try {
//            MqttClient mqttClient = new MqttClient(
//                    "Broker IP",
//                    "clientID",
//                    new MemoryPersistence());
//
//            mqttClient.publish("cmnd/"+topic+"/"+cmnd, new MqttMessage(msg.getBytes()));
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }


}


/**
 * Az automatizációt úgy lehetne megoldani hogy amikor a backend megkapja az üzenetett akkor az eszköz ID alapján lekérje
 * ahhoz az eszközhöz kapcsolódi ifthen-eket és miután lekérdezte akkor ellenőrizze hogy teljesül e valamelyikre,
 * ha igen akkor hajtsa végre az autómatizációt
 */