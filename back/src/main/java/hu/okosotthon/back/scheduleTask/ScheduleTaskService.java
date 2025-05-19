package hu.okosotthon.back.scheduleTask;

import hu.okosotthon.back.Device.DeviceService;
import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    public ScheduleTask saveScheduleTask(ScheduleTask scheduleTask) {
        if(scheduleTask.getId() > -1){
            return this.scheduleTaskRepo.save(scheduleTask);
        }
        Devices devices = this.deviceService.getDeviceById(scheduleTask.getDevice().getDevicesId());
        TasmotaCommand tasmotaCommand = tasmotaCommandService.findByCommandId(scheduleTask.getCommand().getId());
        return this.scheduleTaskRepo.save(new ScheduleTask(scheduleTask, devices, tasmotaCommand));
    }

    public List<ScheduleTaskDTO> getAllScheduleForTime() {
        return this.scheduleTaskRepo.findAllScheduleTasksForTime( LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public List<ScheduleTaskDTO> getAllScheduleForCondition() {
        return this.scheduleTaskRepo.findAllScheduleTasksForCondition();
    }

    public void deleteSchedule(int id) {
        this.scheduleTaskRepo.deleteById(id);
    }

    public List<ScheduleTask> getAllDeviceSchedule(int deviceId) {
        return this.scheduleTaskRepo.getScheduleTaskByDeviceIs(deviceId);
    }

    public List<ScheduleTaskDTO> getAllSchedule() {
        return this.scheduleTaskRepo.findAllScheduleTasks();
    }
}
