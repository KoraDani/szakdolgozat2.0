package hu.okosotthon.back.DeviceSensor;

import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.Sensor.Sensor;
import jakarta.persistence.*;


@Entity
@Table(name = "device_sensor")
public class DeviceSensor {
    @EmbeddedId
    private DeviceSensorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviceId", insertable = false, updatable = false)
    private Devices device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", insertable = false, updatable = false)
    private Sensor sensor;

}

