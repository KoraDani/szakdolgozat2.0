package hu.okosotthon.back.scheduleTask;

import hu.okosotthon.back.Device.DeviceRepo;
import hu.okosotthon.back.Device.DeviceService;
import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.Sensor.SensorRepo;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleTaskService {

    private ScheduleTaskRepo scheduleTaskRepo;
    private DeviceService deviceService;
    private TasmotaCommandService tasmotaCommandService;

    @Autowired
    public ScheduleTaskService(ScheduleTaskRepo scheduleTaskRepo, DeviceService deviceService,TasmotaCommandService tasmotaCommandService) {
        this.scheduleTaskRepo = scheduleTaskRepo;
        this.deviceService = deviceService;
        this.tasmotaCommandService = tasmotaCommandService;
    }


    public ScheduleTask saveScheduleTask(ScheduleTaskDTO scheduleTask) {
        Devices devices = this.deviceService.getDeviceById(scheduleTask.getDevices_id());
        TasmotaCommand tasmotaCommand = tasmotaCommandService.getCommandById(scheduleTask.getCommand_id());
        return this.scheduleTaskRepo.save(new ScheduleTask(scheduleTask, devices, tasmotaCommand));
    }

    public List<ScheduleTask> getAllSchedule() {
        return this.scheduleTaskRepo.findAll();
    }

    public void deleteSchedule(ScheduleTaskDTO scheduleTask) {
        this.scheduleTaskRepo.deleteById(scheduleTask.getId());
    }

}
