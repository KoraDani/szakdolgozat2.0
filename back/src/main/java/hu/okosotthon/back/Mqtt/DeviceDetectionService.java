package hu.okosotthon.back.Mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.okosotthon.back.Device.DetectedDevice;
import hu.okosotthon.back.Device.GPIO;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DeviceDetectionService implements MqttMessageListener {

    private DetectedDevice detectedDevice = new DetectedDevice();
    private List<WebSocModel> webSocModel = new ArrayList<>();

    private boolean statusCheck = false;

    private boolean noStatusSNS = false;

    private final MqttMessagingService mqttMessagingService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public DeviceDetectionService(MqttMessagingService mqttMessagingService, SimpMessagingTemplate messagingTemplate) {
        this.mqttMessagingService = mqttMessagingService;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        if (topic.startsWith("stat/")) {
            try {
                new JSONObject(message.getPayload());
                if (statusCheck) {
                    statusCheck(topic, new String(message.getPayload()));
                }
                HashMap<Object, Object> result = new ObjectMapper().readValue(message.getPayload(), HashMap.class);
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
            } catch (RuntimeException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!detectedDevice.getGpio().isEmpty()) {
            sendDetectedDevice(topic);

        }
    }

    public void sendDetectedDevice(String topic) {
        System.out.println(4 + topic);

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
//        setAutoDetection(false);
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

    public void statusCheck(String topic, String msg) {
        for (WebSocModel ws : this.webSocModel) {
            if (topic.contains(ws.getTopic())) {
                messagingTemplate.convertAndSend(ws.getListen(), msg);
            }
        }
        setStatusCheck(false);
    }

    public void setWebSocModel(WebSocModel webSocModel) {
        webSocModel.getMessage().forEach(msg -> {
            if (!"cmnd/".equals(msg.getPrefix())) {
                this.mqttMessagingService.subscribe(msg.getPrefix() + webSocModel.getTopic() + msg.getPostfix());
            }
            if ("cmnd/".equals(msg.getPrefix())) {
                this.mqttMessagingService.publish(msg.getPrefix() + webSocModel.getTopic() + msg.getPostfix(), msg.getMsg());
            }
        });
        this.webSocModel.add(webSocModel);
    }

    public void setStatusCheck(boolean statusCheck) {
        this.statusCheck = statusCheck;
    }
}

/*

        {"POWER":"OFF",
        "Dimmer":83,
        "Color":"D402020000",
        "HSBColor":"0,100,83",
        "White":0,
        "CT":153,
        "Channel":[83,1,1,0,0]}


 */