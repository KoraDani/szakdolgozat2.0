package hu.okosotthon.back.Sensor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sensorId;
    private String sensorName;
    @Column(columnDefinition = "JSON")
    private String fieldJSON;
    private String category;

    public Sensor(Sensor s) {
        this.sensorId = s.getSensorId();
        this.sensorName = s.getSensorName();
        this.fieldJSON = s.getFieldJSON();
    }


    @Override
    public String toString() {
        return "Sensor{" +
                "sensorId=" + sensorId +
                ", sensorName='" + sensorName + '\'' +
                ", fieldJSON='" + fieldJSON + '\'' +
                '}';
    }
}
