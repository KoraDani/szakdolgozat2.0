package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Devices;
import hu.okosotthon.back.model.Topics;
import org.json.JSONObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends MongoRepository<Topics, String> {
//    void updateTopicsByTopic(Topics topics);
}
