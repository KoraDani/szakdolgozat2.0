package hu.okosotthon.back.NotUsing;

import hu.okosotthon.back.Auth.AuthController;
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

    public Topic saveTopic(String topic){
        if(AuthController.currentUser == null){
            return null;
        }
        return this.topicRepo.save(new Topic(topic,AuthController.currentUser.getUserId()));
    }

    public List<Topic> findAllUserTopics(int userId){
        return this.topicRepo.findAllByUserId(userId);
    }
}
