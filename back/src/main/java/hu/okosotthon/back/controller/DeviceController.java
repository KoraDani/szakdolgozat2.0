package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.service.DeviceService;
import hu.okosotthon.back.service.MqttService;
import hu.okosotthon.back.service.MeasurementService;
import hu.okosotthon.back.service.UsersService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;
    private MeasurementService measurementService;
    private UsersService usersService;
    private MqttService mqttService;

    @Autowired
    public DeviceController(DeviceService deviceService, MeasurementService measurementService, UsersService usersService, MqttService mqttService) {
        this.deviceService = deviceService;
        this.measurementService = measurementService;
        this.usersService = usersService;
        this.mqttService = mqttService;
    }


    @PostMapping("/saveDevice")
    public ResponseEntity<Devices> saveDevices( @RequestBody Devices devices/*, String[] array*/) throws MqttException {
        devices.setUsername(AuthController.currentUser.getUsername());
        Devices newDevice = this.deviceService.saveDevice(devices);
//        System.out.println(AuthController.currentUser.getId());
//        System.out.println(devices.getDeviceName());
        this.measurementService.saveTopic(devices.getTopic());
        this.usersService.updateUsersById(AuthController.currentUser, devices.getTopic());
        this.mqttService.subscribeToTopic(devices.getTopic());
//        System.out.println(array[0]);
        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    @GetMapping("/getAllUserDevices")
    public ResponseEntity<List<Devices>> getAllUserDevices(){
        List<Devices> devicesList = this.deviceService.getAllUserDevices(AuthController.currentUser.getUsername());
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
