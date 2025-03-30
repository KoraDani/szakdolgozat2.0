package hu.okosotthon.back.Device;

import hu.okosotthon.back.Auth.Users;
import hu.okosotthon.back.Auth.UsersRepo;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Sensor.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    private DeviceRepo deviceRepo;
    private UsersRepo usersRepo;
    private SensorRepo sensorRepo;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo, UsersRepo usersRepo, SensorRepo sensorRepo) {
        this.deviceRepo = deviceRepo;
        this.usersRepo = usersRepo;
        this.sensorRepo = sensorRepo;
    }

    public boolean save(DeviceDTO d, int userId){
        Users user = this.usersRepo.getById(userId);
        List<Sensor> sensors = new ArrayList<>();

        for (int sensorId : d.getSensorId()) {
            System.out.println("Sensor id: " + sensorId);
            sensors.add(this.sensorRepo.findBySensorId(sensorId));
        }

        Devices devices = new Devices(d.getDeviceName(),d.getLocation(), user, sensors, d.getTopic(), 1);
        System.out.println(devices.toString());
        return this.deviceRepo.save(devices) != null;
    }

    public List<DeviceDTO> getUserDevices(int userId){
        return this.deviceRepo.getAllByUserId(userId);
    }

    public Devices updateDevice(DeviceDTO deviceDTO){
        Devices devices = this.deviceRepo.getById(deviceDTO.getDeviceId());
        devices.setDeviceName(deviceDTO.getDeviceName());
        devices.setLocation(deviceDTO.getLocation());
        devices.setTopic(deviceDTO.getTopic());
        List<Sensor> sensorList = new ArrayList<>();
        for (int sensorId: deviceDTO.getSensorId()) {
            sensorList.add(this.sensorRepo.findBySensorId(sensorId));
        }
        devices.setSensor(sensorList);
        return this.deviceRepo.save(devices);
    }

    public Devices deleteDevice(int devicesId){
        Devices devices = this.deviceRepo.getById(devicesId);
        devices.setActive(0);
        return this.deviceRepo.save(devices);
    }

    public Devices getDeviceByTopic(String topic) {
        return this.deviceRepo.getDevicesByTopic(topic);
    }

    public DeviceDTO getDeviceDTOById(int deviceId) {
        return  this.deviceRepo.getDevicesByDevicesId(deviceId);
    }

    public Devices getDeviceById(int deviceId) {
        return  this.deviceRepo.getDevicesByDevicesId2(deviceId);
    }

}
