package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Devices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends JpaRepository<Devices, String> {

    @Query("SELECT d FROM Devices d WHERE d.userId = ?1")
    List<Devices> getDevicesByUserId(int userId);

    @Query("SELECT d.devicesId FROM Devices d WHERE d.topic = ?1")
    int getDevicesByTopic(String topic);

}
