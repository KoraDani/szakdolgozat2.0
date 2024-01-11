package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.DeviceDTO;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Topic;
import hu.okosotthon.back.service.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;
    private MqttService mqttService;
    private FieldsService fieldsService;

    @Autowired
    public DeviceController(DeviceService deviceService, MqttService mqttService, FieldsService fieldsService) {
        this.deviceService = deviceService;
        this.mqttService = mqttService;
        this.fieldsService = fieldsService;
    }

    @PostMapping("/saveDevice")
    public ResponseEntity<Devices> saveDevices(@RequestBody DeviceDTO devices) throws MqttException {
        Devices newDevice;
            newDevice = this.deviceService.saveDevice(new Devices(devices.getDeviceName(), devices.getDeviceType(), devices.getLocation(), AuthController.currentUser.getUserId(),devices.getTopic()));
            this.mqttService.subscribeToTopic(newDevice.getTopic());
            List<Fields> fieldsList = new ArrayList<>();
            for (int i = 0; i < devices.getFieldKey().length; i++){
                fieldsList.add(new Fields(newDevice.getDevicesId(), devices.getFieldKey()[i],devices.getFieldType()[i]));
            }
            this.fieldsService.saveAll(fieldsList);
//        }
        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    @GetMapping("/getAllUserDevices")
    public ResponseEntity<List<Devices>> getAllUserDevices(){
        List<Devices> devicesList = this.deviceService.getAllUserDevices(AuthController.currentUser.getUserId());
//        for (Devices dev :devicesList) {
//            System.out.println(dev);
//        }
        return new ResponseEntity<>(devicesList, HttpStatus.OK);
    }

    @PostMapping("/sendDataToFront")
    public ResponseEntity<JSONObject> sendDataToFront(JSONObject json){
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


}
