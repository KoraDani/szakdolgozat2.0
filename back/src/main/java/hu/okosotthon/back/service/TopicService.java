package hu.okosotthon.back.service;

import hu.okosotthon.back.model.Topics;
import hu.okosotthon.back.repository.TopicRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private TopicRepo topicRepo;

    @Autowired
    public TopicService(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    public void saveTopic(String topic) {
        this.topicRepo.save(new Topics("", topic, null));
    }
//    public void updateTopicsByTopic(String topic, JSONObject obj){
//        this.topicRepo.updateTopicsByTopic(new Topics("", topic, obj));
//    }
public void save(String topic, JSONObject obj) {
    this.topicRepo.save(new Topics(null,topic, obj));
}
    public List<Topics> getAllUserTopics() {
        return topicRepo.findAll();
    }
}
