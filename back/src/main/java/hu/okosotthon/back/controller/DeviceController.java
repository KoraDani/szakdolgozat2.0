package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("saveDevice")
    public ResponseEntity<Devices> saveDevices(@RequestBody Devices devices){
        Devices newDevice = this.deviceService.saveDevice(devices);
        
        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

}
