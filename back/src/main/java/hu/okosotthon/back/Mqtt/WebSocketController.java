package hu.okosotthon.back.Mqtt;

import hu.okosotthon.back.Device.DeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebSocketController {

    @Autowired
    private DeviceDetectionService deviceDetectionService;


    @MessageMapping("/request")
    @SendTo("/topic/response")
    public String autoDetect(@RequestBody WebSocModel webSocModel) {
        this.deviceDetectionService.setWebSocModel(webSocModel);
//        mqttService.setAutoDetection(true);
        System.out.println(webSocModel.toString());
//        this.mqttMessagingService.subscribe(webSocModel.getTopic());
        return "null";
    }

    //TODO websokcet megcsinálása hogy a státus lekérdezni
    @MessageMapping("/switch")
    @SendTo("/topic/switch")
    public String switchStatus(@RequestBody WebSocModel webSocModel) {
        System.out.println("SWITCH " + webSocModel.getTopic());
//        mqttService.setWebSocModel(webSocModel);
//        mqttService.subscribeToTopic("cmnd/"+webSocModel.getTopic()+"/POWER");
//        mqttService.subscribeToTopic("stat/"+webSocModel.getTopic()+"/RESULT");
        return "null";
    }

    @MessageMapping("/light/power")
    @SendTo("/topic/light")
    public String lightStatus(@Payload WebSocModel webSocModel) {
        System.out.println("LIGHT " + webSocModel.toString());
//        this.mqttService.setStatusCheck(true);
//        mqttService.setWebSocModel(webSocModel);
//        mqttService.subscribeToTopic(webSocModel);
        return "null";
    }

    @MessageMapping("/power")
    @SendTo("/topic/power")
    public String devicePower(@RequestBody WebSocModel webSocModel) {
        this.deviceDetectionService.setWebSocModel(webSocModel);
        this.deviceDetectionService.setStatusCheck(true);
        return "";
    }


}
