package hu.okosotthon.back.Sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SensorRepo extends JpaRepository<Sensor, Integer> {

    @Query("SELECT f from Sensor f WHERE f.sensorId = ?1")
    Sensor findBySensorId(int id);

    Sensor getSensorBySensorName(String sensorName);



    Sensor getAllBySensorId(int sensorId);

    @Query("SELECT s.fieldJSON FROM Sensor s INNER JOIN DeviceSensor ds ON s.sensorId=ds.sensor.sensorId WHERE ds.device.devicesId = ?1")
    List<String> getAllSensorJSONByDeviceId(int deviceId);

    @Query("SELECT new hu.okosotthon.back.Sensor.Sensor(s.sensorId,s.sensorName,s.fieldJSON,s.category) FROM Sensor s INNER JOIN DeviceSensor ds ON s.sensorId=ds.sensor.sensorId WHERE ds.device.devicesId = ?1")
    List<Sensor> findAllByDeviceId(int deviceId);

}
