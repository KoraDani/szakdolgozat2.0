package hu.okosotthon.back.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeviceRepo extends JpaRepository<Devices, Integer> {

    Devices save(Devices devices);

    @Query("SELECT new hu.okosotthon.back.Device.DeviceDTO(d.devicesId,d.deviceName,d.location, d.topic,d.active) FROM Devices d WHERE d.users.userId = ?1 AND d.active = 1")
    List<DeviceDTO> getAllByUserId(int userId);

    @Query("SELECT d FROM Devices d WHERE d.topic = ?1 AND d.active = 1")
    Devices getDevicesByTopic(String topic);

    @Query("SELECT new hu.okosotthon.back.Device.DeviceDTO(d) FROM Devices d WHERE d.devicesId = ?1 AND d.active = 1")
    DeviceDTO getDevicesByDevicesId(int deviceId);

    @Query("SELECT new hu.okosotthon.back.Device.Devices(d) FROM Devices d WHERE d.devicesId = ?1")
    Devices getDevicesByDevicesId2(int deviceId);
}

