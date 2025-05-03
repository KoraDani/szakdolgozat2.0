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
    public ResponseEntity<List<ScheduleTask>> getAllSchedule() {
        return new ResponseEntity<>(this.scheduleTaskService.getAllSchedule(), HttpStatus.OK);
    }

    @PostMapping("/saveSchedule")
    public ResponseEntity<ScheduleTask> scheduleTask(@RequestBody ScheduleTaskDTO scheduleTask){
        return new ResponseEntity<>(this.scheduleTaskService.saveScheduleTask(scheduleTask), HttpStatus.OK);
    }

    @PutMapping("/updateSchedule")
    public ResponseEntity<ScheduleTask> updateSchedule(@RequestBody ScheduleTaskDTO scheduleTask){
        return new ResponseEntity<>(this.scheduleTaskService.saveScheduleTask(scheduleTask), HttpStatus.OK);
    }

    @DeleteMapping("/delteSchedule")
    public ResponseEntity<ScheduleTask> delteSchedule(@RequestBody ScheduleTaskDTO scheduleTask){
        this.scheduleTaskService.deleteSchedule(scheduleTask);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }



}
