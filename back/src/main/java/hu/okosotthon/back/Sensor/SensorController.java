package hu.okosotthon.back.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Sensor>> getAll(){
        return new ResponseEntity<>(this.sensorService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/getByName")
    public ResponseEntity<Sensor> getByName(@RequestParam String sensorName){
        return new ResponseEntity<>(this.sensorService.getByName(sensorName), HttpStatus.OK);
    }
    @PostMapping("/getAllBySensorId")
    public ResponseEntity<List<Sensor>> getAllBySensorId(@RequestBody List<Integer> sensorId){
        return new ResponseEntity<>(this.sensorService.getAllBySensorId(sensorId), HttpStatus.OK);
    }
    @PostMapping("/getByDeviceId")
    public ResponseEntity<List<Sensor>> getByDeviceId(@RequestParam int deviceId){
        return new ResponseEntity<>(this.sensorService.getByDeviceId(deviceId), HttpStatus.OK);
    }

    @PostMapping("/saveSensor")
    public ResponseEntity<Boolean> saveSensor(@RequestBody Sensor sensor){
        if(this.sensorService.save(sensor) != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/updateSensor")
    public ResponseEntity<Boolean> updateSensor(@RequestBody Sensor sensor){
        if(this.sensorService.save(sensor) != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteSensor")
    public ResponseEntity<Boolean> deleteSensor(@RequestParam int sensorId){
        this.sensorService.delete(sensorId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
