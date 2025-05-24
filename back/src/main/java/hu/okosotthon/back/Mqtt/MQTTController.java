package hu.okosotthon.back.Mqtt;

import hu.okosotthon.back.Device.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mqtt")
public class MQTTController {

//    @Autowired
//    private MqttService mqttService;

//    @PostMapping("/sendMessageToDevice")
//    public ResponseEntity<Boolean> sendMessageToDevice(@RequestBody WebSocModel webSocModel){
//        mqttService.setWebSocModel(webSocModel);
//        return new ResponseEntity<>(this.mqttService.sendMessageToDevice(webSocModel), HttpStatus.OK);
//    }
}
