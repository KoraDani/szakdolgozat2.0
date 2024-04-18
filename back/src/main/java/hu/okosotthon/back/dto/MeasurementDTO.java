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
    private String fieldType;
}
