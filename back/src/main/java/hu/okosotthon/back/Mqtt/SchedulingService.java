package hu.okosotthon.back.Mqtt;

import hu.okosotthon.back.Device.DeviceService;
import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.scheduleTask.Frequency;
import hu.okosotthon.back.scheduleTask.ScheduleTaskDTO;
import hu.okosotthon.back.scheduleTask.ScheduleTaskService;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SchedulingService {

    private final MqttMessagingService mqttMessagingService;
    private final ScheduleTaskService scheduleTaskService;
    private final DeviceService deviceService;
    private final TasmotaCommandService tasmotaCommandService;


    @Autowired
    public SchedulingService(MqttMessagingService mqttMessagingService, ScheduleTaskService scheduleTaskService, DeviceService deviceService, TasmotaCommandService tasmotaCommandService) {
        this.mqttMessagingService = mqttMessagingService;
        this.scheduleTaskService = scheduleTaskService;
        this.deviceService = deviceService;
        this.tasmotaCommandService = tasmotaCommandService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkForTimeActions() {
        //TODO scheduleing ot megcsinálni
        System.out.println("checkForActions");
        List<ScheduleTaskDTO> scheduleTask = this.scheduleTaskService.getAllScheduleForTime();
        if (!scheduleTask.isEmpty()) {
            scheduleTask.forEach(task -> {
                Devices d = this.deviceService.getDeviceById(task.getDevices_id());
                TasmotaCommand tc = this.tasmotaCommandService.getCommandById(task.getCommand_id());
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                if (task.getScheduledTime().equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))) {
                    if (task.getFrequency() == Frequency.DAILY) {
                        this.mqttMessagingService.publish("cmnd/"+d.getTopic()+"/"+tc.getCommand(), tc.getArgument());
                    } else if (task.getFrequency() == Frequency.WEEKLY) {
                        this.mqttMessagingService.publish("cmnd/"+d.getTopic()+"/"+tc.getCommand(), tc.getArgument());
                    } else if (task.getFrequency() == Frequency.MONTHLY) {
                        this.mqttMessagingService.publish("cmnd/"+d.getTopic()+"/"+tc.getCommand(), tc.getArgument());
                    }
                }
            });
        }
    }
}
