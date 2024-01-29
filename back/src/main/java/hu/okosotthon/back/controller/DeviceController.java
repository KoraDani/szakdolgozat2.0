package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.DeviceDTO;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.service.*;

import netscape.javascript.JSObject;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
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
        for (int i = 0; i < deviceDTO.getFieldKey().length; i++) {
//            fieldsList.add(new Fields(deviceDTO.getFieldKey()[i],deviceDTO.getFieldType()[i],newDevice));
            this.fieldsService.save(new Fields(deviceDTO.getFieldKey()[i], deviceDTO.getFieldType()[i], newDevice));
        }
//        this.fieldsService.saveAll(fieldsList);

        this.mqttService.subscribeToTopic(newDevice.getTopic());

        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    //TODO valamiért két szer küldi vissza a activ devicot
    @GetMapping("/getAllUserDevices")
    public ResponseEntity<List<DeviceDTO>> getAllUserDevices() {
        List<Devices> dev = this.deviceService.getAllUserDevices(AuthController.currentUser.getUserId());
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        System.out.println("devices szie: " + dev.size());
        int i = 0;
        for (Devices devices : dev) {
            System.out.println(devices.getDeviceName());
            if (devices.getFieldsList() != null) {
//                for (int j = 0; j < devices.getFieldsList().size(); j++) {
//                    if (devices.getMeasurementList() != null && devices.getMeasurementList().size() - 1 > i) {
//                        if (devices.getFieldsList().get(j).getFieldKey().equals(devices.getMeasurementList().get(i).getPayloadKey())) {
                            deviceDTOS.add(devices.convertDivece());
                            i++;
                            System.out.println("hozzadadva");
//                        }
//                    }
//                }
            }
        }
        for (DeviceDTO deviceDTO : deviceDTOS) {
            System.out.println("deviceName: " + deviceDTO.getDeviceName());
        }
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
            @RequestParam(value = "payloadKey") String payloadKey,
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "payload") String payload) {

//        Devices d = this.deviceService.getDeviceByTopic(topic);
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.mqttService.publishDataToDevice(topic, "{\""+payloadKey+"\":\""+payload+"\"}");
        this.measurementService.save(new Measurement(payloadKey, payload.toString(), currentDateTime.toString(), null));
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @PostMapping("/getDeviceById")
    public ResponseEntity<DeviceDTO> getDeviceById(@RequestParam String devicesId){
        System.out.println("getDeviceById lefutott");
        return new ResponseEntity<>(this.deviceService.getDeviceById(Integer.parseInt(devicesId)), HttpStatus.OK);
    }

}
