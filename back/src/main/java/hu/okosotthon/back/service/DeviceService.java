package hu.okosotthon.back.service;

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

    public void deleteDeviceById(int deviceId) {
//        return this.deviceRepo.deleteDevicesByDevicesId(deviceId);
        //TODO a törlés valószínűleg nem így kell megoldani majd holnap meg kell kérdezni
         this.deviceRepo.deleteByDevicesId(deviceId);
    }
}
