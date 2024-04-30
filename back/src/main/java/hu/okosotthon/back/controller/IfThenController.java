package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.IfThenDTO;
import hu.okosotthon.back.model.IfThen;
import hu.okosotthon.back.service.IfThenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ifThen")
public class IfThenController {
    private IfThenService ifThenService;

    @Autowired
    public IfThenController(IfThenService ifThenService) {
        this.ifThenService = ifThenService;
    }

    @PostMapping("/saveIfThen")
    public ResponseEntity<IfThen> saveIfThen(@RequestBody IfThen ifThen){
        return new ResponseEntity<>(this.ifThenService.saveIfThen(ifThen), HttpStatus.OK);
    }

    @PostMapping("/getIfThen")
    public ResponseEntity<List<IfThenDTO>> getIfThen(@RequestParam int deviceId){
        return new ResponseEntity<>(this.ifThenService.getIfThen(deviceId), HttpStatus.OK);
//        return this.ifThenService.getIfThen(AuthController.currentUser, deviceId);
    }
}
