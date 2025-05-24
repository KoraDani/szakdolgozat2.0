package hu.okosotthon.back.Mqtt;

import hu.okosotthon.back.Device.DeviceDTO;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MqttMessagingService {

    @Autowired
    @Lazy
    private MqttConnectionService mqttConnectionService;

//    public MqttMessagingService(MqttConnectionService mqttConnectionService) {
//        this.mqttConnectionService = mqttConnectionService;
//    }

    public void subscribe(String topic) {
        try {
            mqttConnectionService.getClient().subscribe(topic);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to subscribe", e);
        }
    }

    public void publish(String topic, String payload) {
        try{
            mqttConnectionService.getClient().publish(topic, new MqttMessage(payload.getBytes()));
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish", e);
        }
    }

    public void unsubscribe(String topic) {
        try {
            mqttConnectionService.getClient().unsubscribe(topic);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to unsubscribe", e);
        }
    }

    public void sendMessageToDevice(WebSocModel webSocModel) {
        webSocModel.getMessage().forEach((msg) -> {
            this.publish(msg.getPrefix()+webSocModel.getTopic()+msg.getPostfix(), msg.getMsg());
        });
    }

    public void checkAndSubscribe(DeviceDTO deviceDTO) {
        System.out.println(deviceDTO.getTopic());
        deviceDTO.getSensors().forEach((sensor) -> {
            if(sensor.getCategory().equals("temp") || sensor.getCategory().equals("plug")) {
                this.subscribe("tele/"+deviceDTO.getTopic()+"/SENSOR");
            }
        });
    }
}
