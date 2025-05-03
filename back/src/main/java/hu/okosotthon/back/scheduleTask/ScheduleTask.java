package hu.okosotthon.back.scheduleTask;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String scheduledTime;

    private boolean dailySchedule;

    private boolean weeklySchedule;

    private boolean monthlySchedule;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "device_id")
    @JsonBackReference
    private Devices device;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private TasmotaCommand command;

    public ScheduleTask(ScheduleTaskDTO scheduleTask, Devices devices, TasmotaCommand tasmotaCommand) {
        this.scheduledTime = scheduleTask.getScheduledTime();
        this.dailySchedule = scheduleTask.isDailySchedule();
        this.weeklySchedule = scheduleTask.isWeeklySchedule();
        this.monthlySchedule = scheduleTask.isMonthlySchedule();
        this.active = scheduleTask.isActive();
        this.device = devices;
        this.command = tasmotaCommand;
    }
}
