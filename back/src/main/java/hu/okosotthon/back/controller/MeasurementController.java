package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Measurement;
import hu.okosotthon.back.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/topic")
public class MeasurementController {
    private MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }


    @GetMapping("/getAllUserMeasurment")
    public ResponseEntity<List<Measurement>> getAllUserMeasurment(){
        System.out.println("GetAllMeasurment");
        List<Measurement> measurementList = this.measurementService.getAllUserTopics(AuthController.currentUser.getUsername());
//        for (Topics t : topicsList) {
//            System.out.println(t.getTopicId());
//        }
        return new ResponseEntity<>(measurementList, HttpStatus.OK);
    }

    //TODO itt most már csak azt kell megcsinálni hogy az utolsó mérést adja vissza
}
