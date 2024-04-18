package hu.okosotthon.back.dto;

import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO2 {
    @Nullable
    private int devicesId;
    private String deviceName;
    private String deviceType;
    private String location;
    private String topic;
    @Nullable
    private int active;
    @Nullable
    private Map<String, String> fields = new HashMap<>();
    @Nullable
    private Map<String, String> payload = new HashMap<>();


    public DeviceDTO2(int devicesId, String deviceName, String location, String topic) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.location = location;
        this.topic = topic;
    }


    public DeviceDTO2(int devicesId, String deviceName, String location, String topic, List<Fields> fieldsList, List<Measurement> measurementList) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.location = location;
        this.topic = topic;
        for (Fields f: fieldsList) {
            if(!f.getFieldKey().isEmpty()){
                this.fields.put(f.getFieldKey(),f.getFieldType());
            }
        }
        for (Measurement m : measurementList) {
            if(!m.getPayloadKey().isEmpty()){
                this.payload.put(m.getPayloadKey(),m.getPayloadValue());
            }
        }
    }

    public DeviceDTO2(int devicesId, String deviceName, @Nullable Map<String, String> fields) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.fields = fields;
    }
}
