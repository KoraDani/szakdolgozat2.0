package hu.okosotthon.back.Device;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Measurment.Measurement;
import hu.okosotthon.back.Auth.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devices")
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devicesId;
    private String deviceName;
    private String location;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonManagedReference
    private Users users;
    @Nullable
    @OneToMany(mappedBy = "devices", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Measurement> measurementList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "device_sensor",
              joinColumns = @JoinColumn(name = "deviceId"),
    inverseJoinColumns = @JoinColumn(name = "sensorId"))
    private List<Sensor> sensor;

    private String topic;
    private int active;

    public Devices(String deviceName, String location, Users users, List<Sensor> sensor, String topic, int active) {
        this.deviceName = deviceName;
        this.location = location;
        this.users = users;
        this.sensor = sensor;
        this.topic = topic;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Devices{" +
                "devicesId=" + devicesId +
                ", deviceName='" + deviceName + '\'' +
                ", location='" + location + '\'' +
                ", users=" + users +
                ", measurementList=" + measurementList +
                ", sensor=" + sensor +
                ", topic='" + topic + '\'' +
                ", active=" + active +
                '}';
    }
}
