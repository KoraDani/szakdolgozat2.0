package hu.okosotthon.back.scheduleTask;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


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

    private Frequency frequency;

    private int targetDeviceId;

    @Nullable
    private String whichValue;

    @Nullable
    private String conditionOperator;

    @Nullable
    private int whenCondition;

    private boolean active;


    @ManyToOne
    @JoinColumn(name = "devices_id")
    @JsonBackReference
    private Devices device;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private TasmotaCommand command;

    public ScheduleTask(ScheduleTask scheduleTask, Devices devices, TasmotaCommand tasmotaCommand) {
        this.scheduledTime = scheduleTask.getScheduledTime();
        this.frequency = scheduleTask.getFrequency();
        this.targetDeviceId = scheduleTask.getTargetDeviceId();
        this.whichValue = scheduleTask.getWhichValue();
        this.conditionOperator = scheduleTask.getConditionOperator();
        this.whenCondition = scheduleTask.getWhenCondition();
        this.active = scheduleTask.isActive();
        this.device = devices;
        this.command = tasmotaCommand;
    }
}
