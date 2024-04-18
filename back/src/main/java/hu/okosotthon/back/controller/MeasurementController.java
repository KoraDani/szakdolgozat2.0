package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.MeasurementDTO;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    //TODO fronton nem használom
    @GetMapping("/getAllUserMeasurment")
    public ResponseEntity<List<Measurement>> getAllUserMeasurment(){
        System.out.println("GetAllMeasurment");
        List<Measurement> measurementList = this.measurementService.findMeasurementByUserId();
        return new ResponseEntity<>(measurementList.isEmpty() ? null : measurementList, HttpStatus.OK);
    }

//    @PostMapping("/getAllMeasurementByDeviceId")
//    public ResponseEntity<List<Measurement>> getAllMeasurementByDeviceId(@RequestParam int deviceId){
//        List<Measurement> measurementList = this.measurementService.getAllMeasurementByDeviceId(deviceId);
//        return new ResponseEntity<>( measurementList.subList(measurementList.size()-50, measurementList.size()), HttpStatus.OK);
//    }

    @GetMapping("/getDeviceMeasuremenet")
    public ResponseEntity<List<MeasurementDTO>> getDeviceMeasurement(@RequestParam int devicesId){
        return new ResponseEntity<>(this.measurementService.getDeviceMeasurement(devicesId), HttpStatus.OK);
    }

    @PostMapping("/getMeasurementByField")
    public ResponseEntity<List<Measurement>> getMeasurementByField(@RequestParam int devicesId, @RequestParam String fieldKey){
        System.out.println("Field key: " + fieldKey);
        List<Measurement> measurementList = this.measurementService.getMeasurementListByField(devicesId,fieldKey);
        return new ResponseEntity<>(measurementList, HttpStatus.OK);
    }

    @PostMapping("/getMapOfListMeasurment")
    public ResponseEntity<Map<String,List<Measurement>>> getMapOfListMeasurment(@RequestParam int devicesId){
        System.out.println("getMapOfListMeasurment: " + devicesId);
        return new ResponseEntity<>(this.measurementService.getMapOfListMeasurment(devicesId), HttpStatus.OK);
    }

    @PostMapping("/getListOfListMeasurment")
    public ResponseEntity<List<List<MeasurementDTO>>> getListOfListMeasurment(@RequestParam int devicesId){
        System.out.println("getListOfListMeasurment: " + devicesId);
        return new ResponseEntity<>(this.measurementService.getListOfListMeasurment(devicesId), HttpStatus.OK);
    }

}
