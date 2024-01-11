package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Objects;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Users findUsersByUsername(String username);


//    Users findUsersById(String userId);
}
