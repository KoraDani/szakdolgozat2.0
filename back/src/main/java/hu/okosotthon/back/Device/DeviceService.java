package hu.okosotthon.back.Device;

import hu.okosotthon.back.Auth.Users;
import hu.okosotthon.back.Auth.UsersRepo;
import hu.okosotthon.back.Measurment.MeasurementRepo;
import hu.okosotthon.back.Sensor.SensorRepo;
import hu.okosotthon.back.config.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private DeviceRepo deviceRepo;
    private UsersRepo usersRepo;
    private SensorRepo sensorRepo;
    private UserAuthProvider userAuthProvider;
    private MeasurementRepo measurementRepo;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo, UsersRepo usersRepo, SensorRepo sensorRepo, UserAuthProvider userAuthProvider,MeasurementRepo measurementRepo) {
        this.deviceRepo = deviceRepo;
        this.usersRepo = usersRepo;
        this.sensorRepo = sensorRepo;
        this.userAuthProvider = userAuthProvider;
        this.measurementRepo = measurementRepo;
    }

    public Devices save(DeviceDTO d, int userId) {
        Users user = this.usersRepo.findUsersByUserId(userId);

        System.out.println("DeviceDTO Sensors: " + d.getSensors());

        Devices devices = new Devices(d.getDeviceName(), d.getLocation(), user, d.getSensors(), d.getTopic(), 1);
        return this.deviceRepo.save(devices);
    }

    public List<DeviceDTO> getUserDevices(int userId) {
        List<DeviceDTO> deviceDTOList = this.deviceRepo.getAllByUserId(userId);
        for (DeviceDTO d: deviceDTOList) {
            d.setSensors(this.sensorRepo.findAllByDeviceId(d.getDevicesId()));
        }

        return deviceDTOList;
    }

    public Devices updateDevice(DeviceDTO deviceDTO, String token) {

        Users users = this.usersRepo.findUsersByUserId(this.userAuthProvider.getUserId(token));

        return this.deviceRepo.saveAndFlush(new Devices(deviceDTO.getDevicesId(), deviceDTO.getDeviceName(), deviceDTO.getLocation(), users, null, null, null,deviceDTO.getTopic(),  deviceDTO.getActive()));
    }

    public Devices deleteDevice(int devicesId) {
        Devices devices = this.deviceRepo.getById(devicesId);
        devices.setActive(0);
        return this.deviceRepo.save(devices);
    }

    public Devices getDeviceByTopic(String topic) {
        return this.deviceRepo.getDevicesByTopic(topic);
    }

    public DeviceDTO getDeviceDTOById(int deviceId) {
        return this.deviceRepo.getDevicesByDevicesId(deviceId);
    }

    public Devices getDeviceById(int deviceId) {
        return this.deviceRepo.getDevicesByDevicesId2(deviceId);
    }

}
