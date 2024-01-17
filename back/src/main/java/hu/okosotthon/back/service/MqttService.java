package hu.okosotthon.back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Measurement;
import netscape.javascript.JSObject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MqttService {

    private MeasurementService measurementService;
    private DeviceService deviceService;
    private UsersService usersService;

    @Autowired
    public MqttService(MeasurementService measurementService, DeviceService deviceService, UsersService usersService) {
        this.measurementService = measurementService;
        this.deviceService = deviceService;
        this.usersService = usersService;
    }

    String broker = "tcp://192.168.0.171:1883";
    //A ClientId az lehetne aki ben van jelentkezve
    String clientId = "SpringBootSubscriber";
    MqttClient mqttClient;


    public void connectToBroker() {
        try {
            mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
//            subscribeToTopic("asd");
            messageArrived();
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }



    public void subscribeToTopic(String topic) throws MqttException {
        mqttClient.subscribe(topic);
//        if(AuthController.currentUser != null){
//            List<String> userTopicList = this.usersService.getSubscribedTopicsFromUser();
//            for (String t: userTopicList) {
//                mqttClient.subscribe(t);
//            }
//        }
    }

    public void messageArrived(){
        mqttClient.setCallback(new MqttCallback() {

            @Override
            @Async
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Received message on topic: " + topic);
                System.out.println("Message: " + new String(message.getPayload()));
//                int deviceId = deviceService.getDeviceIdByTopic(topic);
//                System.out.println("deviceId: " + deviceId);
                LocalDateTime currentDateTime = LocalDateTime.now();
                Devices devices = deviceService.getDeviceByTopic(topic);
                    ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> jsonMap = objectMapper.readValue(new String(message.getPayload()), Map.class);
                System.out.println(jsonMap.get("temp"));
                for (Map.Entry<String,String> entry : jsonMap.entrySet()){
                    System.out.println("Measurement save: " + devices.getDevicesId());
                    measurementService.save(new Measurement(String.valueOf(entry.getKey()),String.valueOf(entry.getValue()), currentDateTime.toString(), devices));
                }
//                handleAsyncMessage(topic, new String(message.getPayload()));
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost"+ cause);
//                connectToBroker();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Not used in this example
            }
        });
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
}
