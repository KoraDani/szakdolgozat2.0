package hu.okosotthon.back.service;

import hu.okosotthon.back.dto.DeviceDTO;

import hu.okosotthon.back.dto.DeviceDTO2;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.repository.DeviceRepo;
import hu.okosotthon.back.repository.FieldsRepo;
import hu.okosotthon.back.repository.MeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.font.TextHitInfo;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {
    private DeviceRepo deviceRepo;
    private FieldsRepo fieldsRepo;
    private MeasurementRepo measurementRepo;
    @Autowired
    public DeviceService(DeviceRepo deviceRepo, FieldsRepo fieldsRepo, MeasurementRepo measurementRepo) {
        this.deviceRepo = deviceRepo;
        this.fieldsRepo = fieldsRepo;
        this.measurementRepo = measurementRepo;
    }

    public Devices saveDevice(Devices devices){
        return this.deviceRepo.save(devices);
    }

    public List<DeviceDTO> getAllUserDevices(int userId) {
//        SELECT * FROM `devices` INNER JOIN fields ON devices.devicesId=fields.devicesId INNER JOIN measurement ON devices.devicesId=measurement.devicesId WHERE devices.userId = 26 AND fields.fieldKey=measurement.payloadKey AND measurement.payloadKey='proba'
//        ORDER BY measurement.time DESC
//        limit 1;
        List<Devices> devicesList = this.deviceRepo.getDevicesByUserId(userId);
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        int i = 0;
        for (Devices d : devicesList) {
            List<Fields> fieldsList = this.fieldsRepo.getAllDeviceFields(d.getDevicesId());
            List<Measurement> measurementList = new ArrayList<>();
            if(!fieldsList.isEmpty()){
                System.out.println(fieldsList.get(i).toString());
                i++;
                for (Fields f : fieldsList) {
                    measurementList.addAll(this.measurementRepo.getMeasurementByField(d.getDevicesId(),f.getFieldKey(), PageRequest.of(0,1)));
                }
                deviceDTOS.add(new DeviceDTO(d.getDevicesId(),d.getDeviceName(),d.getLocation(),d.getTopic(), fieldsList, measurementList));
            }
        }
        return deviceDTOS;
    }

    public List<DeviceDTO2> getAllUserDevices2(int userId) {
//        SELECT * FROM `devices` INNER JOIN fields ON devices.devicesId=fields.devicesId INNER JOIN measurement ON devices.devicesId=measurement.devicesId WHERE devices.userId = 26 AND fields.fieldKey=measurement.payloadKey AND measurement.payloadKey='proba'
//        ORDER BY measurement.time DESC
//        limit 1;
        List<Devices> devicesList = this.deviceRepo.getDevicesByUserId(userId);
        List<DeviceDTO2> deviceDTOS = new ArrayList<>();
        int i = 0;
        for (Devices d : devicesList) {
            List<Fields> fieldsList = this.fieldsRepo.getAllDeviceFields(d.getDevicesId());
            List<Measurement> measurementList = new ArrayList<>();
            if(!fieldsList.isEmpty()){
//                System.out.println(fieldsList.get(i).toString());
                i++;
                for (Fields f : fieldsList) {
                    measurementList.addAll(this.measurementRepo.getMeasurementByField(d.getDevicesId(),f.getFieldKey(), PageRequest.of(0,1)));
                }
                deviceDTOS.add(new DeviceDTO2(d.getDevicesId(),d.getDeviceName(),d.getLocation(),d.getTopic(), fieldsList, measurementList));
            }
        }
        return deviceDTOS;
    }

    public int getDeviceIdByTopic(String topic) {
        return this.deviceRepo.getDevicesIdByTopic(topic);
    }
    public Devices getDeviceByTopic(String topic){
        return this.deviceRepo.getDeviceByTopic(topic);
    }

    public void setDeviceDeletedByDeviceId(int deviceId) {
//        return this.deviceRepo.deleteDevicesByDevicesId(deviceId);
         this.deviceRepo.setDeviceDeletedByDeviceId(deviceId);
    }

    public Devices getDeviceById(int devicesId) {return this.deviceRepo.getDevicesById(devicesId);}


    public Devices getDeviceTest() {
        return this.deviceRepo.getDevicesById(23);
    }
}
