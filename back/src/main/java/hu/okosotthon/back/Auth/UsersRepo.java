package hu.okosotthon.back.Auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Users findUsersByUsername(String username);

    Users findUsersByEmail(String email);


}
