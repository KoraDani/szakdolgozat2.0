package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String measurementId;
    private int deviceId;
    @Nullable
    private String payload;
    private String time;
//    @Nullable
//    private String dateTime;

    public Measurement(int deviceId, @Nullable String payload) {
        this.deviceId = deviceId;
        this.payload = payload;
    }

    public Measurement(int deviceId, @Nullable String payload, String time) {
        this.deviceId = deviceId;
        this.payload = payload;
        this.time = time;
    }
}


