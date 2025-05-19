package hu.okosotthon.back.tasmotaCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tasmota")
public class TasmotaCommandController {

    private TasmotaCommandService tasmotaCommandService;

    @Autowired
    TasmotaCommandController(TasmotaCommandService tasmotaCommandService) {
        this.tasmotaCommandService = tasmotaCommandService;
    }

    @GetMapping("/command")
    public ResponseEntity<List<TasmotaCommand>> getAllCommand() {
        return new ResponseEntity<>(this.tasmotaCommandService.getAllCommand(), HttpStatus.OK);
    }
}
