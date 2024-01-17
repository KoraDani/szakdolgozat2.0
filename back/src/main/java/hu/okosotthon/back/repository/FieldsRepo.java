package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FieldsRepo extends JpaRepository<Fields, Integer> {

    @Query("SELECT f FROM Fields f INNER JOIN Devices d ON f.devices.devicesId=d.devicesId WHERE d.users.userId = ?1")
    List<Fields> getAllByUserId(int userId);
}
