package hu.okosotthon.back.tasmotaCommand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasmotaCommandRepo extends JpaRepository<TasmotaCommand, Integer> {
    TasmotaCommand findTasmotaCommandByCommand(String command);
}
