package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.DeviceDTO2;
import hu.okosotthon.back.dto.FieldDTO;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.service.FieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fields")
public class FieldsController {
    private FieldsService fieldsService;

    @Autowired
    public FieldsController(FieldsService fieldsService) {
        this.fieldsService = fieldsService;
    }

    @GetMapping("/getAllDeviceFields")
    public ResponseEntity<List<Fields>> getAllDeviceFields(){
        List<Fields> fieldsList = this.fieldsService.getAllFieldsByUserId();
        return new ResponseEntity<>(fieldsList, HttpStatus.OK);
    }

    @PostMapping("/getDevicesFieldsByOutput")
    public ResponseEntity<List<FieldDTO>> getDevicesFieldsByOutput(){
        return new ResponseEntity<>(this.fieldsService.getDevicesFieldsByOutput(AuthController.currentUser.getUserId()), HttpStatus.OK);
    }
}
