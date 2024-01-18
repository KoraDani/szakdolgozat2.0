package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.DeviceDTO;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.service.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
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
        List<Fields> fieldsList = new ArrayList<>();

        System.out.println(devices.getDeviceName());


        Devices newDevice = this.deviceService.saveDevice(new Devices(devices.getDeviceName(), devices.getDeviceType(), devices.getLocation(), AuthController.currentUser,devices.getTopic()));
        for (int i = 0; i < devices.getFieldKey().length; i++){
//            fieldsList.add(new Fields(devices.getFieldKey()[i],devices.getFieldType()[i],newDevice));
            this.fieldsService.save(new Fields(devices.getFieldKey()[i],devices.getFieldType()[i],newDevice));
        }
//        this.fieldsService.saveAll(fieldsList);

        this.mqttService.subscribeToTopic(newDevice.getTopic());

        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    @GetMapping("/getAllUserDevices")
    public ResponseEntity<List<DeviceDTO>> getAllUserDevices(){
        List<Devices> dev = this.deviceService.getAllUserDevices(AuthController.currentUser.getUserId());
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        System.out.println("devices szie: " + dev.size());
        int i = 0;
        for (Devices devices : dev) {
            if(devices.getFieldsList() != null){
                for (int j = 0; j < devices.getFieldsList().size(); j++) {
                    if(devices.getMeasurementList() != null && devices.getMeasurementList().size()-1 > i){
                        if(devices.getFieldsList().get(j).getFieldKey().equals(devices.getMeasurementList().get(i).getPayloadKey())){
                            deviceDTOS.add(devices.convertDivece());
                            i++;
                            System.out.println("hozzadadva");
                        }
                    }
                }
            }
        }
        for (DeviceDTO deviceDTO: deviceDTOS) {
            System.out.println("deviceName: " + deviceDTO.getDeviceName());
        }
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @PostMapping("/deleteDevice")
    public ResponseEntity<Integer> deleteDevice(@RequestBody int deviceId){
        System.out.println("deleted devicesid: " +deviceId);
        this.deviceService.deleteDeviceById(deviceId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
