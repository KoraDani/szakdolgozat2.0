package hu.okosotthon.back.repository;


import hu.okosotthon.back.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<Users, Integer> {
}
