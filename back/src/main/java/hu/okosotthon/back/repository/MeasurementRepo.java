package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Measurement;
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
}
