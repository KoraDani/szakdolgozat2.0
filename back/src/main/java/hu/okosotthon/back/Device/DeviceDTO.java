package hu.okosotthon.back.Device;

import hu.okosotthon.back.Measurment.Measurement;
import hu.okosotthon.back.Sensor.Sensor;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeviceDTO {
    @Nullable
    private int deviceId;
    private String deviceName;
    @Nullable
    private List<Sensor> sensors = new ArrayList<>();
    @Nullable
    private List<Measurement> measurements = new ArrayList<>();
    private String location;
    private String topic;
    private int active;

    public DeviceDTO(Devices devices) {
        this.deviceId = devices.getDevicesId();
        this.deviceName = devices.getDeviceName();
        this.sensors.addAll(devices.getSensor());
        this.measurements.addAll(devices.getMeasurementList());
        this.location = devices.getLocation();
        this.topic = devices.getTopic();

    }

    public DeviceDTO(int deviceId, String deviceName, String location, String topic, int active) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.location = location;
        this.topic = topic;
        this.active = active;
    }
}
