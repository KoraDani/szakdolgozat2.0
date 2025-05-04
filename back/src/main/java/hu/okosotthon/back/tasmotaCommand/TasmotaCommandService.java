package hu.okosotthon.back.tasmotaCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasmotaCommandService {
    private TasmotaCommandRepo tasmotaCommandRepo;

    @Autowired
    TasmotaCommandService(TasmotaCommandRepo tasmotaCommandRepo) {
        this.tasmotaCommandRepo = tasmotaCommandRepo;
    }

    public TasmotaCommand getCommandById(int commandId) {
        return this.tasmotaCommandRepo.findById(commandId).get();
    }

    public TasmotaCommand findByCommand(String command) {
        return this.tasmotaCommandRepo.findTasmotaCommandByCommand(command);
    }
}
