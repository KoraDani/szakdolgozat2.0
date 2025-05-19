package hu.okosotthon.back.scheduleTask;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/scheduleTask")
public class ScheduleTaskController {
    private ScheduleTaskService scheduleTaskService;

    @Autowired
    public ScheduleTaskController(ScheduleTaskService scheduleTaskService) {
        this.scheduleTaskService = scheduleTaskService;
    }

    @GetMapping("/getAllSchedule")
    public ResponseEntity<List<ScheduleTaskDTO>> getAllSchedule() {
        return new ResponseEntity<>(this.scheduleTaskService.getAllSchedule(), HttpStatus.OK);
    }

    @PostMapping("/getAllDeviceSchedule")
    public ResponseEntity<List<ScheduleTask>> getAllDeviceSchedule(@RequestParam int deviceId) {
        return new ResponseEntity<>(this.scheduleTaskService.getAllDeviceSchedule(deviceId), HttpStatus.OK);
    }

    @PostMapping("/saveSchedule")
    public ResponseEntity<ScheduleTask> scheduleTask(@RequestBody ScheduleTask scheduleTask){
        System.out.println(scheduleTask.toString());
        return new ResponseEntity<>(this.scheduleTaskService.saveScheduleTask(scheduleTask), HttpStatus.OK);
//        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/updateSchedule")
    public ResponseEntity<ScheduleTask> updateSchedule(@RequestBody ScheduleTask scheduleTask){
        return new ResponseEntity<>(this.scheduleTaskService.saveScheduleTask(scheduleTask), HttpStatus.OK);
    }

    @DeleteMapping("/delteSchedule")
    public ResponseEntity<ScheduleTask> delteSchedule(@RequestParam int id){
        this.scheduleTaskService.deleteSchedule(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }



}
