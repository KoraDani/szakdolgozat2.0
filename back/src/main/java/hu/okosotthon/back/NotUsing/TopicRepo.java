package hu.okosotthon.back.NotUsing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Integer> {
    List<Topic> findAllByUserId(int userId);
}
