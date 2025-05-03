package hu.okosotthon.back.Measurment;

import hu.okosotthon.back.Sensor.SensorRepo;
import hu.okosotthon.back.scheduleTask.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private MeasurementRepo measurementRepo;
    private SensorRepo sensorRepo;
    private ScheduleTaskService scheduleTaskService;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo, SensorRepo sensorRepo, ScheduleTaskService scheduleTaskService) {
        this.measurementRepo = measurementRepo;
        this.sensorRepo = sensorRepo;
        this.scheduleTaskService = scheduleTaskService;
    }

    public List<Measurement> getMeasurementByDevIdAndType( int devicesId, String type, int peagable) {
        Pageable pageable = PageRequest.of(0, peagable, Sort.Direction.DESC, "time");
        List<Measurement> measurementList = this.measurementRepo.findAllByDeviceIdAndType(devicesId, type, pageable);

        return measurementList;
    }

//    public List<Measurement> getMeasurementByDevIdAndType( int devicesId, int peagable) {
//        List<String> jsonSensor = this.sensorRepo.getAllSensorJSONByDeviceId(devicesId);
//        List<Measurement> measurementList = new ArrayList<>();
//
//        Gson gson = new Gson();
//        List<String> sensorList = new ArrayList<>();
//
//        jsonSensor.forEach(s -> sensorList.addAll(gson.fromJson(s, ArrayList.class)));
//
//        Pageable pageable = PageRequest.of(0, peagable, Sort.Direction.DESC, "time");
//        sensorList.forEach(s -> measurementList.addAll(this.measurementRepo.findAllByDeviceId(s, devicesId, pageable)));
//
//        return measurementList;
//    }

    public Measurement save(Measurement measurement) {
        return this.measurementRepo.save(measurement);
    }

    public Measurement updateMeasurment(Measurement measurement) {
        return this.measurementRepo.save(measurement);
    }

    public void delete(int measurmentId) {
        this.measurementRepo.deleteById(measurmentId);
    }
}
