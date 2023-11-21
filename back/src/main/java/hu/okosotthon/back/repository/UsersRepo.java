package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Objects;
import java.util.Optional;

public interface UsersRepo extends MongoRepository<Users, Integer> {
    void deleteUsersById(Long id);
    Optional<Users> findUsersById(int id);
    Users findUsersById(String userId);
    Users findUsersByUsername(String username);
//    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    Users findUsersByEmail(String email);
    Users getUsersByUsername(String username);


//    Users findUsersById(String userId);
}
