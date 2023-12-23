package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Topics;
import hu.okosotthon.back.service.DeviceService;
import hu.okosotthon.back.service.TopicService;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;
    private TopicService topicService;

    @Autowired
    public DeviceController(DeviceService deviceService, TopicService topicService) {
        this.deviceService = deviceService;
        this.topicService = topicService;
    }




    @PostMapping("/saveDevice")
    public ResponseEntity<Devices> saveDevices( @RequestBody Devices devices/*, String[] array*/){
        Devices newDevice = this.deviceService.saveDevice(devices);
        System.out.println(devices.getDeviceName());
        this.topicService.saveTopic(devices.getTopic());
//        System.out.println(array[0]);
        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    @PostMapping("/getAllUserDevices")
    public ResponseEntity<List<Devices>> getAllUserDevices(@RequestBody String userId){
        System.out.println(userId);
        List<Devices> devicesList = this.deviceService.getAllUserDevices(userId);
        for (Devices dev :devicesList) {
            System.out.println(dev);
        }
        return new ResponseEntity<>(devicesList, HttpStatus.OK);
    }
}
