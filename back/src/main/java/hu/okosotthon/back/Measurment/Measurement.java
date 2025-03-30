package hu.okosotthon.back.Measurment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.okosotthon.back.Device.Devices;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;



@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private int measurementId;
    private String sensorType;
    private String value;
    private String time;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "devicesId")
    private Devices devices;

    public Measurement(String sensorType, String value, String time, Devices devices) {
        this.sensorType = sensorType;
        this.value = value;
        this.time = time;
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "measurementId=" + measurementId +
                ", sensor_type='" + sensorType + '\'' +
                ", value='" + value + '\'' +
                ", time='" + time + '\'' +
                ", devices=" + devices +
                '}';
    }
}


