package hu.okosotthon.back.IfThen;

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

}
