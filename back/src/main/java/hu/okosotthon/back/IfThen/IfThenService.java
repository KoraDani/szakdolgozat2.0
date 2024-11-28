package hu.okosotthon.back.IfThen;

import hu.okosotthon.back.Device.DeviceRepo;
import hu.okosotthon.back.Sensor.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IfThenService {
    private IfThenRepo ifThenRepo;
    private DeviceRepo deviceRepo;
    private SensorRepo sensorRepo;

    @Autowired
    public IfThenService(IfThenRepo ifThenRepo, DeviceRepo deviceRepo, SensorRepo sensorRepo) {
        this.ifThenRepo = ifThenRepo;
        this.deviceRepo = deviceRepo;
        this.sensorRepo = sensorRepo;
    }

    public IfThen saveIfThen(IfThen ifThen) {
        ifThen.setTime(LocalDateTime.now().toString());
        return this.ifThenRepo.save(ifThen);
    }

    public List<IfThen> getAllIfThen() {
        return this.ifThenRepo.findAll();
    }

    public void delete(){}
}
