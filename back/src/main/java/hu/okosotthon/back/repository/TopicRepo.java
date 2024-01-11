package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Integer> {
    List<Topic> findAllByUserId(int userId);
}
