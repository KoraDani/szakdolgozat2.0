package hu.okosotthon.back.Auth;

import hu.okosotthon.back.Auth.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    Users findUsersByUsername(String username);


}
