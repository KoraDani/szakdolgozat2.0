package hu.okosotthon.back.Measurment;

import hu.okosotthon.back.Sensor.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/getMeasurementByDevIdAndType")
    public ResponseEntity<List<Measurement>> getMeasurementByDevIdAndType(@RequestParam int deviceId, @RequestParam String type, @RequestParam int peagable){
        System.out.println(deviceId);
        List<Measurement> measurements = this.measurementService.getMeasurementByDevIdAndType(deviceId, type,peagable);
        if(!measurements.isEmpty()){
            return new ResponseEntity<>(measurements, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    //TODO nem biztos hogy kelleni fog
    @PostMapping("/updateMeasurment")
    public ResponseEntity<Boolean> updateMeasurment(@RequestBody Measurement measurement){
        if(this.measurementService.updateMeasurment(measurement) != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    //TODO nem biztos hogy kelleni fog
    @DeleteMapping("/deleteMeasurment")
    public ResponseEntity<Boolean> deleteMeasurment(@RequestParam int measurmentId){
        this.measurementService.delete(measurmentId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
