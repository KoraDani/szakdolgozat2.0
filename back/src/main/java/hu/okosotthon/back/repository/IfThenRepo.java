package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.IfThen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IfThenRepo extends JpaRepository<IfThen, Integer> {
    @Query("SELECT i FROM IfThen i WHERE i.ifDeviceId = ?1")
    List<IfThen> findAllByIfDeviceId(int deviceId);
}
