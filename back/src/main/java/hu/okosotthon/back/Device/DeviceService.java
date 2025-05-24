package hu.okosotthon.back.Device;

import hu.okosotthon.back.Auth.Users;
import hu.okosotthon.back.Auth.UsersRepo;
import hu.okosotthon.back.Measurment.MeasurementRepo;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Sensor.SensorRepo;
import hu.okosotthon.back.config.UserAuthProvider;
import hu.okosotthon.back.exception.DuplicateTopicException;
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

    public Devices save(DeviceDTO deviceDTO, int userId) {
        Devices d = this.getDeviceByTopic(deviceDTO.getTopic());

        if(d != null) {
            throw new DuplicateTopicException("A device with topic '" + d.getTopic() + "' already exists.");
        }


        Users user = this.usersRepo.findUsersByUserId(userId);

        System.out.println("DeviceDTO Sensors: " + deviceDTO.getSensors());

        Devices devices = new Devices(deviceDTO.getDeviceName(), deviceDTO.getLocation(), user, deviceDTO.getSensors(), deviceDTO.getTopic(), 1);
        return this.deviceRepo.save(devices);
    }

    public List<DeviceDTO> getUserDevices(int userId) {
        List<DeviceDTO> deviceDTOList = this.deviceRepo.getAllByUserId(userId);
        for (DeviceDTO d: deviceDTOList) {
            d.setSensors(this.sensorRepo.findAllByDeviceId(d.getDevicesId()));
        }

        return deviceDTOList;
    }

    public Devices updateDevice(DeviceDTO deviceDTO, int userId) {

        Users users = this.usersRepo.findUsersByUserId(userId);

        return this.deviceRepo.saveAndFlush(new Devices(deviceDTO.getDevicesId(), deviceDTO.getDeviceName(), deviceDTO.getLocation(), users, null, deviceDTO.getSensors(), null,deviceDTO.getTopic(),  deviceDTO.getActive()));
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
