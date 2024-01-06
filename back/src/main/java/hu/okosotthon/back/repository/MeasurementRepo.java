package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Measurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepo extends MongoRepository<Measurement, String> {
//    void updateTopicsByTopic(Topics topics);
//    Map<String, String> findAll();
    List<Measurement> findAllByUsername(String username);
}
