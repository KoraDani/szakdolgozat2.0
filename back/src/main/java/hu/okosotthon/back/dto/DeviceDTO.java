package hu.okosotthon.back.dto;

import hu.okosotthon.back.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
    private String[] fieldKey;
    private String[] fieldType;
    @Nullable
    private String[] payloadKey;
    @Nullable
    private String[] payloadValue;
}
