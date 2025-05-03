package hu.okosotthon.back.Device;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Measurment.Measurement;
import hu.okosotthon.back.Auth.Users;
import hu.okosotthon.back.scheduleTask.ScheduleTask;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.config.ScheduledTask;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devices")
@ToString
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devicesId;
    private String deviceName;
    private String location;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Users users;
    @Nullable
    @OneToMany(mappedBy = "devices", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Measurement> measurementList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "device_sensor",
            joinColumns = @JoinColumn(name = "deviceId"),
            inverseJoinColumns = @JoinColumn(name = "sensorId"))
    @Nullable
    private List<Sensor> sensor;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private List<ScheduleTask> scheduledTasks;

    private String topic;
    @Nullable
    private int active;

    public Devices(int devicesId, String deviceName, String location, String topic) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.location = location;
        this.topic = topic;
    }

    public Devices(String deviceName, String location, Users users, List<Sensor> sensor, String topic, int active) {
        this.deviceName = deviceName;
        this.location = location;
        this.users = users;
        this.sensor = sensor;
        this.topic = topic;
        this.active = active;
    }
}
