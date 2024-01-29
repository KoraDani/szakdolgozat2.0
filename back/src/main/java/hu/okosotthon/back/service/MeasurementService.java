package hu.okosotthon.back.service;

import hu.okosotthon.back.controller.AuthController;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.model.Topic;
import hu.okosotthon.back.repository.MeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }


    public void save(Measurement measurement) {
        this.measurementRepo.save(measurement);
    }

    public List<Measurement> findMeasurementByUserId() {
        return this.measurementRepo.findMeasurementByUserId(AuthController.currentUser.getUserId());
    }

    public List<Measurement> getAllMeasurementByDeviceId(int deviceId) {
        return this.measurementRepo.getAllMeasurementByDeviceId(deviceId);
    }
}
