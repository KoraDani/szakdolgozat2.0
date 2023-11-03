package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    void deleteUsersById(Long id);

    Optional<Users> findUsersById(int id);

    Users findUsersByUsername(String username);
    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    Users findUsersByEmail(String email);
}
