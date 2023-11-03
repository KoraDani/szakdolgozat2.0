package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Devices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepo extends JpaRepository<Devices, Integer> {
}
