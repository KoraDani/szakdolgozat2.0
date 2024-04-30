package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.DeviceDTO;

import hu.okosotthon.back.dto.DeviceDTO2;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.service.*;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;
    private MqttService mqttService;
    private FieldsService fieldsService;
    private MeasurementService measurementService;

    @Autowired
    public DeviceController(DeviceService deviceService, MqttService mqttService, FieldsService fieldsService, MeasurementService measurementService) {
        this.deviceService = deviceService;
        this.mqttService = mqttService;
        this.fieldsService = fieldsService;
        this.measurementService = measurementService;
    }



    @PostMapping("/saveDevice")
    public ResponseEntity<Devices> saveDevices(@RequestBody DeviceDTO deviceDTO) throws MqttException {
        List<Fields> fieldsList = new ArrayList<>();

        System.out.println(deviceDTO.getDeviceName());


        Devices newDevice = this.deviceService.saveDevice(new Devices(deviceDTO.getDeviceName(), deviceDTO.getDeviceType(), deviceDTO.getLocation(), AuthController.currentUser, deviceDTO.getTopic()));
        for (int i = 0; i < deviceDTO.getFieldKey().size(); i++) {
//            fieldsList.add(new Fields(deviceDTO.getFieldKey()[i],deviceDTO.getFieldType()[i],newDevice));
            this.fieldsService.save(new Fields(deviceDTO.getFieldKey().get(i), deviceDTO.getFieldType().get(i), newDevice));
        }
//        this.fieldsService.saveAll(fieldsList);

        this.mqttService.subscribeToTopic(newDevice.getTopic());

        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    //TODO egyenlőre nem működik
    @GetMapping("/getAllUserDevices")
    public ResponseEntity<List<DeviceDTO>> getAllUserDevices() {
        List<DeviceDTO> deviceDTOS = this.deviceService.getAllUserDevices(AuthController.currentUser.getUserId());
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllUserDevices2")
    public ResponseEntity<List<DeviceDTO2>> getAllUserDevices2() {
        List<DeviceDTO2> deviceDTOS = this.deviceService.getAllUserDevices2(AuthController.currentUser.getUserId());
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @PostMapping("/deleteDevice")
    public ResponseEntity<Integer> deleteDevice(@RequestBody int deviceId) {
        System.out.println("deleted devicesid: " + deviceId);
        this.deviceService.setDeviceDeletedByDeviceId(deviceId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/sendPayloadToDevice")
    public ResponseEntity<String> sendPayloadToDevice(
            @RequestBody DeviceDTO device,
            @RequestParam(value = "payloadKey") String payloadKey,
            @RequestParam(value = "payload") String payload
    ) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.mqttService.publishDataToDevice(device.getTopic(), "{\""+payloadKey+"\":\""+payload+"\"}");
        this.measurementService.save(new Measurement(payloadKey, payload.toString(), currentDateTime.toString(), this.deviceService.getDeviceById(device.getDevicesId())));
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }


    /**
     * Ez csak tesztelés céljából van itt
     * */
    @GetMapping("/getDeviceTest")
    public ResponseEntity<Devices> getDeviceTest(){
        return new ResponseEntity<>(this.deviceService.getDeviceTest(), HttpStatus.OK);
    }

}
