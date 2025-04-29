package hu.okosotthon.back.Device;

import hu.okosotthon.back.Mqtt.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;
    private MqttService mqttService;

    @Autowired
    public DeviceController(DeviceService deviceService,MqttService mqttService) {
        this.deviceService = deviceService;
        this.mqttService = mqttService;
    }

    //Creat
    @PostMapping("/saveDevice")
    public ResponseEntity<Devices> saveDevice(@RequestBody DeviceDTO deviceDTO, @RequestParam int userId) {
        this.mqttService.subscribeToTopic2(deviceDTO.getTopic());
        return new ResponseEntity<>(this.deviceService.save(deviceDTO, userId), HttpStatus.OK);
    }

    //Read
    @PostMapping("/getUserDevices")
    public ResponseEntity<List<DeviceDTO>> getUserDevices(@RequestParam int userId) {
        return new ResponseEntity<>(this.deviceService.getUserDevices(userId), HttpStatus.OK);
    }

    @PostMapping("/getDeviceDTOById")
    public ResponseEntity<DeviceDTO> getDeviceDTOById(@RequestParam int deviceId){
        return new ResponseEntity<>(this.deviceService.getDeviceDTOById(deviceId), HttpStatus.OK);
    }
    @PostMapping("/getDeviceById")
    public ResponseEntity<Devices> getDeviceById(@RequestParam int deviceId){
        return new ResponseEntity<>(this.deviceService.getDeviceById(deviceId), HttpStatus.OK);
    }

    //Update
    @PostMapping("/updateDevice")
    public ResponseEntity<Boolean> updateDevice(@RequestBody DeviceDTO deviceDTO, @RequestHeader("Authorization") String token) {
        if (this.deviceService.updateDevice(deviceDTO, token) != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    //Delete
    @DeleteMapping("/deleteDevice")
    public ResponseEntity<Boolean> deleteDevice(@RequestParam int devicesId) {
        if(this.deviceService.deleteDevice(devicesId).getActive() == 0){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }


}
