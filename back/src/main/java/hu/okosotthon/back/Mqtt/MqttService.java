package hu.okosotthon.back.Mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//@Service
public class MqttService {

    //    private boolean autoDetection = false;
//    private boolean isStatusCheck = false;
//
//    private List<WebSocModel> webSocModel = new ArrayList<>();
//    private final MqttConnectionService connectionService;
//    private final MqttMessagingService messagingService;
//    private final DeviceDetectionService deviceDetectionService;
//    private final SensorProcessingService sensorProcessingService;
//    private final SchedulingService schedulingService;
//
//
//    @Autowired
//    public MqttService(
//            MqttConnectionService connectionService,
//            MqttMessagingService messagingService,
//            DeviceDetectionService deviceDetectionService,
//            SensorProcessingService sensorProcessingService,
//            SchedulingService schedulingService
//    ) {
//        this.connectionService = connectionService;
//        this.messagingService = messagingService;
//        this.deviceDetectionService = deviceDetectionService;
//        this.sensorProcessingService = sensorProcessingService;
//        this.schedulingService = schedulingService;
//    }


//    public void connectToBroker() {
//
//    }
//
//    public void setAutoDetection(boolean val) {
//        this.autoDetection = val;
//    }
//
//    public void setStatusCheck(boolean val) {
//        this.isStatusCheck = val;
//    }
//
//    public void setWebSocModel(WebSocModel webSocModel) {
//        this.webSocModel.add(webSocModel);
//    }
//
//    public void subscribeToTopic(WebSocModel webSocModel) {
//        System.out.println("Subscribing to websocket and topic");
//        try {
//            for (MessageModel m : webSocModel.getMessage()) {
//                String tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
//                mqttClient.subscribe(tmpTopic);
//
//
//                if (m.getPrefix().contains("cmnd")) {
//                    tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
//                    mqttClient.publish(tmpTopic, new MqttMessage(m.getMsg().getBytes()));
//                }
//
//                if (m.getPrefix().contains("stat")) {
//                    tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
//                    System.out.println(tmpTopic);
//                    mqttClient.subscribe(tmpTopic);
//                }
//
//            }
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void subscribeToTopic2(String topic) {
//        try {
//            mqttClient.subscribe("tele/" + topic + "/SENSOR");
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean sendMessageToDevice(WebSocModel webSocModel) {
//        try {
//            for (MessageModel m : webSocModel.getMessage()) {
//                String tmpTopic = m.getPrefix() + webSocModel.getTopic() + m.getPostfix();
//                mqttClient.publish(tmpTopic, new MqttMessage(m.getMsg().getBytes()));
//            }
//            return true;
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//
//    public void messaging() {
//        mqttClient.setCallback(new MqttCallback() {
//
//            @Override
//            @Async
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                String msg = new String(message.getPayload());
//                System.out.println(msg + "  Messasge arrived");
//                if (msg.length() > 2) {
//                    try {
//                        System.out.println(msg);
//                        new JSONObject(msg);
//
//                        if (isStatusCheck) {
//                        }
//
//                        if (topic.startsWith("stat/") && autoDetection) {
//                            STATMapper(msg, topic);
//                        }
//
//                        if (topic.startsWith("tele/") || topic.endsWith("/SENSOR")) {
//                            TELEMapper(msg, topic);
//                        }
//
//
//                        if (!detectedDevice.getGpio().isEmpty()) {
//                            sendDetectedDevice(topic);
//                        }
//
//
//                    } catch (JSONException e) {
//                        System.err.println(e);
//                    }
//                }
//            }
//
//            @Override
//            public void connectionLost(Throwable cause) {
//                System.out.println("Connection lost" + cause);
//                connectToBroker();
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//                // Not used in this example
//            }
//
//        });
//    }
//
//    public void statusCheck(String topic, String msg) {
//        for (WebSocModel ws : this.webSocModel) {
//            if (topic.contains(ws.getTopic())) {
//                messagingTemplate.convertAndSend(ws.getListen(), msg);
//                this.webSocModel.remove(ws);
//            }
//        }
//        setStatusCheck(false);
//    }
//
//    public void sendDetectedDevice(String topic) {
//        if (detectedDevice.getDeviceName().isEmpty()) {
//            detectedDevice.setDeviceName("Null");
//        }
//        System.out.println("device is not null");
//        WebSocModel tmp = new WebSocModel();
//        for (WebSocModel ws : this.webSocModel) {
//            if (topic.contains(ws.getTopic())) {
//                messagingTemplate.convertAndSend(ws.getListen(), detectedDevice);
//                detectedDevice = new DetectedDevice();
//                tmp = ws;
//                break;
//            }
//        }
//        this.webSocModel.remove(tmp);
//        System.out.println(this.webSocModel.toString());
//        System.out.println("Device is being cleard");
//        System.out.println(detectedDevice.toString());
//        setAutoDetection(false);
//    }
//
//    boolean automation = false;
//
//
//    public void TELEMapper(String jsonObject, String topic) {
//
//
//
//    }
//
//
//
//    @Scheduled(cron = "0 * * * * *")
//    public void checkForConditionActions() {
//        automation = true;
//        System.out.println("checkForConditionActions");
//        scheduleTask = this.scheduleTaskService.getAllScheduleForCondition();
//        if (!scheduleTask.isEmpty()) {
//            scheduleTask.forEach(task -> {
//                Devices d = this.deviceService.getDeviceById(task.getDevices_id());
//
//                subscribeToTopic2(d.getTopic());
//                sendMessagetoDevice(d.getTopic(), "STATUS", "8");
//
//                unsubscribeFromTopic(d.getTopic(), "STATUS");
//            });
//        }
//    }


//    public void sendMessagetoDevice(String topic, String cmnd, String argument) {
//        try {
//            System.out.println("cmnd/" + topic + "/" + cmnd);
//            this.mqttClient.publish("cmnd/" + topic + "/" + cmnd, new MqttMessage(argument.getBytes()));
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void unsubscribeFromTopic(String topic, String cmnd) {
//        try {
//            this.mqttClient.unsubscribe("cmnd/"+ topic + "/"+cmnd);
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//    }

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