package hu.okosotthon.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeasurementDTO {
    @Nullable
    private int measurementId;
    private String payloadKey;
    private String payloadValue;
    private String time;
    @Nullable
    private int fieldId;
    private String fieldType;
    @Nullable
    private int devicesId;

    public MeasurementDTO(int measurementId, String payloadKey, String payloadValue, String time, String fieldType) {
        this.measurementId = measurementId;
        this.payloadKey = payloadKey;
        this.payloadValue = payloadValue;
        this.time = time;
        this.fieldType = fieldType;
    }
}
