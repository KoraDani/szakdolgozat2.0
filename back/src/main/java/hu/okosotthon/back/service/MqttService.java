package hu.okosotthon.back.service;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private MeasurementService measurementService;
    private UsersService usersService;

    @Autowired
    public MqttService(MeasurementService measurementService, UsersService usersService) throws MqttException {
        this.measurementService = measurementService;
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
        String temperatureTopic = "esp32/dht11/temperature";
        String humidityTopic = "esp32/dht11/humidity";
        String tasmotaTopic = "tele/home/dolgozo/temp/SENSOR";

        //Topicnál pedig amikor egy felhasználó hozzáad egy eszközt akkor meg kell adnia topicot és azt
        //a topicot eltároljuk egy listában és mindig azon a listán végig iterálva fogunk feliratkozni a topicocra

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
            @Async // Make the method asynchronous
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Received message on topic: " + topic);
                System.out.println("Message: " + new String(message.getPayload()));
//                JSONObject obj = new JSONObject(new String(message.getPayload()));
//                System.out.println("JSON obj: " + obj.get("DHT11"));
                measurementService.save(topic, new String(message.getPayload()));
                // Delegate message handling to the service
//                handleAsyncMessage(topic, new String(message.getPayload()));
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost"+ cause);
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
