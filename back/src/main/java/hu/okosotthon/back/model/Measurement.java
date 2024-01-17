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
    private String measurementId;
//    private int deviceId;
    @Nullable
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

    public Measurement(@Nullable String payloadKey, String payloadValue, String time, int devicesId) {
        this.payloadKey = payloadKey;
        this.payloadValue = payloadValue;
        this.time = time;
        this.devices.setDevicesId(devicesId);
    }

    //    public Measurement(int deviceId, @Nullable String payload) {
//        this.deviceId = deviceId;
//        this.payload = payload;
//    }

//    public Measurement(int deviceId, @Nullable String payload, String time) {
//        this.deviceId = deviceId;
//        this.payload = payload;
//        this.time = time;
//    }
}


