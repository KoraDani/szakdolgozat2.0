package hu.okosotthon.back.scheduleTask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleTaskRepo extends JpaRepository<ScheduleTask, Integer> {

    @Query("SELECT new hu.okosotthon.back.scheduleTask.ScheduleTaskDTO(st) FROM ScheduleTask st")
    List<ScheduleTaskDTO> findAllScheduleTasks();

    @Query("SELECT st FROM ScheduleTask st WHERE st.device.devicesId = ?1")
    List<ScheduleTask> getScheduleTaskByDeviceIs(int deviceId);

    @Query("SELECT new hu.okosotthon.back.scheduleTask.ScheduleTaskDTO(st) FROM ScheduleTask st WHERE st.scheduledTime = ?1 AND st.device IS NOT NULL")
    List<ScheduleTaskDTO> findAllScheduleTasksForTime(String time);

    @Query("SELECT new hu.okosotthon.back.scheduleTask.ScheduleTaskDTO(st) FROM ScheduleTask st WHERE st.frequency = 0")
    List<ScheduleTaskDTO> findAllScheduleTasksForCondition();

}
