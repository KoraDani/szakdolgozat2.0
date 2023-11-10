package hu.okosotthon.back.repository;


import hu.okosotthon.back.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisterRepo extends MongoRepository<Users, Integer> {
}
