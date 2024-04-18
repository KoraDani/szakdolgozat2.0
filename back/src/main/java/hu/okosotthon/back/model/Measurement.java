package hu.okosotthon.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;


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
//    private int deviceId;
    private String payloadKey;
    private String payloadValue;
    private String time;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "devicesId")
    private Devices devices;


    public Measurement(@Nullable String payloadKey, String payloadValue, String time, Devices devices) {
        this.payloadKey = payloadKey;
        this.payloadValue = payloadValue;
        this.time = time;
        this.devices = devices;
    }

    public Measurement(int measurementId, String payloadKey, String payloadValue, String time) {
        this.measurementId = measurementId;
        this.payloadKey = payloadKey;
        this.payloadValue = payloadValue;
        this.time = time;
    }

    @Override
    public String toString() {
        return '{' +
                "measurementId=" + measurementId +
                ", payloadKey='" + payloadKey + '\'' +
                ", payloadValue='" + payloadValue + '\'' +
                ", time='" + time + '\'' +
                ", devices=" + devices.getDevicesId() +
                '}';
    }
}


