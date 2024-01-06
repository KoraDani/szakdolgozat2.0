package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Devices;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends MongoRepository<Devices, String> {

//    @Query(value = "{'userId':'?0'}")
    List<Devices> getDevicesByUsername(String userId);
//    List<Devices> findAllByUserId(String userId);
}
