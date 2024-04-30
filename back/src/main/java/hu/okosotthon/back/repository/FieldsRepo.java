package hu.okosotthon.back.repository;

import hu.okosotthon.back.dto.FieldDTO;
import hu.okosotthon.back.model.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FieldsRepo extends JpaRepository<Fields, Integer> {

    @Query("SELECT f FROM Fields f INNER JOIN Devices d ON f.devices.devicesId=d.devicesId WHERE d.users.userId = ?1")
    List<Fields> getAllByUserId(int userId);

    @Query("SELECT f FROM Fields f WHERE f.devices.devicesId = ?1 ORDER BY f.fieldType ASC")
    List<Fields> getAllDeviceFields(int deviceId);

    @Query("SELECT new hu.okosotthon.back.dto.FieldDTO(f.fieldId, d.devicesId, d.deviceName, f.fieldKey, f.fieldType) FROM Fields f  INNER JOIN Devices d ON f.devices.devicesId=d.devicesId WHERE f.fieldType = '2' AND d.users.userId = ?1")
    List<FieldDTO> getDevicesFieldsByOutput(int userId);

    @Query("SELECT f FROM Fields f WHERE f.fieldId = ?1")
    Fields getFieldsById(int fieldId);
}
