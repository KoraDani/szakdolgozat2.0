package hu.okosotthon.back.tasmotaCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasmotaCommandService {
    private TasmotaCommandRepo tasmotaCommandRepo;

    @Autowired
    TasmotaCommandService(TasmotaCommandRepo tasmotaCommandRepo) {
        this.tasmotaCommandRepo = tasmotaCommandRepo;
    }

    public TasmotaCommand getCommandById(int commandId) {
        return this.tasmotaCommandRepo.findById(commandId).orElse(null);
    }

    public TasmotaCommand findByCommandId(int id) {
        return this.tasmotaCommandRepo.findTasmotaCommandById(id);
    }

    public List<TasmotaCommand> getAllCommand() {
        return this.tasmotaCommandRepo.findAll();
    }
}
