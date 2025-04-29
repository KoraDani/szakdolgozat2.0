package hu.okosotthon.back.Measurment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Integer> {

    @Query("SELECT m FROM Measurement m WHERE m.sensorType = ?1 AND m.devices.devicesId = ?2")
    List<Measurement> findAllByDeviceId(String s, int deviceId, Pageable pageable);

    @Query("SELECT m FROM Measurement m WHERE m.devices.devicesId = ?1 AND m.sensorType = ?2")
    List<Measurement> findAllByDeviceIdAndType(int devicesId, String type, Pageable pageable);
}
