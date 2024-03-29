package hu.okosotthon.back.service;

import hu.okosotthon.back.dto.DeviceDTO;
import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.repository.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private DeviceRepo deviceRepo;
    @Autowired
    public DeviceService(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Devices saveDevice(Devices devices){
        return this.deviceRepo.save(devices);
    }

    public List<Devices> getAllUserDevices(int userId) {
        return this.deviceRepo.getDevicesByUserId(userId);
//        return this.deviceRepo.findAll();
//        return this.deviceRepo.findAllByUserId(userId);
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

    public DeviceDTO getDeviceById(int devicesId) {
        return this.deviceRepo.getDevicesById(devicesId).convertDivece();
    }
}
