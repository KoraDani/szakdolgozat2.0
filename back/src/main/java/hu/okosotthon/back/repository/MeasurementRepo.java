package hu.okosotthon.back.repository;

import hu.okosotthon.back.dto.MeasurementDTO;
import hu.okosotthon.back.model.Measurement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, String> {
//    void updateTopicsByTopic(Topics topics);
//    Map<String, String> findAll();
    @Query("SELECT m FROM Measurement m INNER JOIN Devices d ON d.devicesId=m.devices.devicesId WHERE d.users.userId = ?1")
    List<Measurement> findMeasurementByUserId(int userId);

    @Query("SELECT m FROM Measurement m WHERE m.devices.devicesId = ?1")
    List<Measurement> getAllMeasurementByDeviceId(int deviceId);

    @Query("SELECT new hu.okosotthon.back.dto.MeasurementDTO(m.measurementId, m.payloadKey, m.payloadValue, m.time, '0') FROM Measurement m WHERE m.devices.devicesId = ?1 AND m.payloadKey = ?2 ORDER BY m.time DESC")
    List<MeasurementDTO> getDevicesMeasurementByFields(int devicesId, String payloadKey, Pageable pageable);

    @Query("SELECT m FROM Measurement m WHERE m.devices.devicesId = ?1 AND m.payloadKey = ?2 ORDER BY m.time DESC ")
    List<Measurement> getMeasurementByField(int devicesId, String fieldKey, Pageable pageable);

}
