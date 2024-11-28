package hu.okosotthon.back.Device;

import hu.okosotthon.back.Sensor.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    @Nullable
    private int deviceId;
    private String deviceName;
    private List<Integer> sensorId = new ArrayList<>();
    private String location;
    private String topic;

    public DeviceDTO(Devices devices) {
        this.deviceId = devices.getDevicesId();
        this.deviceName = devices.getDeviceName();
        for (Sensor s : devices.getSensor()) {
            this.sensorId.add(s.getSensorId());
        }
        this.location = devices.getLocation();
        this.topic = devices.getTopic();
    }
}
