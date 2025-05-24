package hu.okosotthon.back.Mqtt;

import hu.okosotthon.back.Device.DeviceDTO;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqttMessaging")
public class MqttMessagingController {

    private final MqttMessagingService mqttMessagingService;

    @Autowired
    public MqttMessagingController(MqttMessagingService mqttMessagingService) {
        this.mqttMessagingService = mqttMessagingService;
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody WebSocModel webSocModel) {
        this.mqttMessagingService.sendMessageToDevice(webSocModel);
    }

    @PostMapping("/subscribeToTopic")
    public void subscribeToTopic(@RequestBody DeviceDTO deviceDTO) {
        this.mqttMessagingService.checkAndSubscribe(deviceDTO);
    }
}
