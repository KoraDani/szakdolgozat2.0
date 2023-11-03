package hu.okosotthon.back.service;

import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.repository.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
