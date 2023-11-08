package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/saveDevice")
    public ResponseEntity<Devices> saveDevices( @RequestBody Devices devices, String[] array){
        Devices newDevice = this.deviceService.saveDevice(devices);
        System.out.println(devices.getDeviceName());
        System.out.println(array[0]);
        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }
}
