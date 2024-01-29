package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/getAllUserMeasurment")
    public ResponseEntity<List<Measurement>> getAllUserMeasurment(){
        System.out.println("GetAllMeasurment");
        List<Measurement> measurementList = this.measurementService.findMeasurementByUserId();
        return new ResponseEntity<>(measurementList.isEmpty() ? null : measurementList, HttpStatus.OK);
    }

    @PostMapping("/getAllMeasurementByDeviceId")
    public ResponseEntity<List<Measurement>> getAllMeasurementByDeviceId(@RequestParam int deviceId){
        List<Measurement> measurementList = this.measurementService.getAllMeasurementByDeviceId(deviceId);
        return new ResponseEntity<>( measurementList.subList(measurementList.size()-50, measurementList.size()), HttpStatus.OK);
    }
}
