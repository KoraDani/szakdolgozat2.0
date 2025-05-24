package hu.okosotthon.back.DeviceSensor;

import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.Sensor.Sensor;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "device_sensor")
public class DeviceSensor {
    @EmbeddedId
    private DeviceSensorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviceId", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Devices device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", insertable = false, updatable = false)
    private Sensor sensor;

}

