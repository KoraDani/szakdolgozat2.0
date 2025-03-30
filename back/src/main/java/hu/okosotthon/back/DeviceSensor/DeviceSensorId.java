package hu.okosotthon.back.DeviceSensor;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DeviceSensorId implements Serializable {

    private Integer deviceId;
    private Integer sensorId;

    // Getters and setters
}
