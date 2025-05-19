package hu.okosotthon.back.scheduleTask;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleTaskDTO {
    private int id;

    private String scheduledTime;

    private Frequency frequency;

    private boolean active;

    private int targetDeviceId;

    private String whichValue;

    private String condition;

    private int when;

    private int devices_id;
    private int command_id;

    ScheduleTaskDTO(ScheduleTask scheduleTask) {
        this.scheduledTime = scheduleTask.getScheduledTime();
        this.frequency = scheduleTask.getFrequency();
        this.active = scheduleTask.isActive();
        this.targetDeviceId = scheduleTask.getTargetDeviceId();
        this.whichValue = scheduleTask.getWhichValue();
        this.condition = scheduleTask.getConditionOperator();
        this.when = scheduleTask.getWhenCondition();
        this.devices_id = scheduleTask.getDevice().getDevicesId();
        this.command_id = scheduleTask.getCommand().getId();
    }
}
