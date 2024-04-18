package hu.okosotthon.back.dto;

import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    @Nullable
    private int devicesId;
    private String deviceName;
    private String deviceType;
    private String location;
    private String topic;
    @Nullable
    private int active;
    @Nullable
    private List<String> fieldKey = new ArrayList<>();
    @Nullable
    private List<String> fieldType= new ArrayList<>();
    @Nullable
    private List<String> payloadKey= new ArrayList<>();
    @Nullable
    private List<String> payloadValue= new ArrayList<>();
    @Nullable
    private List<String> time = new ArrayList<>();;


    public DeviceDTO(int devicesId, String deviceName, String location, String topic) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.location = location;
        this.topic = topic;
    }


    public DeviceDTO(int devicesId, String deviceName, String location, String topic, List<Fields> fieldsList, List<Measurement> measurementList) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.location = location;
        this.topic = topic;
        for (Fields f: fieldsList) {
            if(!f.getFieldKey().isEmpty()){
                this.fieldKey.add(f.getFieldKey());
                this.fieldType.add(f.getFieldType());
            }
        }
        for (Measurement m : measurementList) {
            if(!m.getPayloadKey().isEmpty()){
                this.payloadKey.add(m.getPayloadKey());
                this.payloadValue.add(m.getPayloadValue());
            }
        }
    }
}
