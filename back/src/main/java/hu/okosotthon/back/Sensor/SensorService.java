package hu.okosotthon.back.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorService {
    private SensorRepo sensorRepo;

    @Autowired
    public SensorService(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

    public Sensor save(Sensor sensor){
        return this.sensorRepo.save(sensor);
    }

    public void delete(int sensorId){
        this.sensorRepo.deleteById(sensorId);
    }

    public List<Sensor> getAll(){
        return this.sensorRepo.findAll();
    }


    public Sensor getByName(String sensorName) {
        return this.sensorRepo.getSensorBySensorName(sensorName);
    }

    public List<Sensor> getAllBySensorId(List<Integer> sensorId) {
        List<Sensor> sensorList = new ArrayList<>();
        for (int id : sensorId) {
            sensorList.add(this.sensorRepo.getAllBySensorId(id));
        }
        return sensorList;
    }

    public List<String> getAllByDeviceId(int deviceId) {
        return this.sensorRepo.getAllSensorJSONByDeviceId(deviceId);
    }

    public List<Sensor> getByDeviceId(int deviceId) {
        return this.sensorRepo.getByDeviceId(deviceId);
    }
}
