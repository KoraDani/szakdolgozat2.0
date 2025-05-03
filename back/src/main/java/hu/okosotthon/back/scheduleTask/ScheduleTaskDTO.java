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

    private boolean dailySchedule;

    private boolean weeklySchedule;

    private boolean monthlySchedule;

    private boolean active;

    private int devices_id;
    private int command_id;
}
