package hu.okosotthon.back.service;

import hu.okosotthon.back.controller.AuthController;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.repository.MeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }

    public void saveTopic(String topic) {
        this.measurementRepo.save(new Measurement("",AuthController.currentUser.getUsername(), topic, null));
    }

    //    public void updateTopicsByTopic(String topic, JSONObject obj){
//        this.topicRepo.updateTopicsByTopic(new Topics("", topic, obj));
//    }
    public void save(String topic, String obj) {
        this.measurementRepo.save(new Measurement(null, AuthController.currentUser.getUsername(), topic, obj));
    }

    public List<Measurement> getAllUserTopics(String username) {
        return this.measurementRepo.findAllByUsername(username);
    }
}
