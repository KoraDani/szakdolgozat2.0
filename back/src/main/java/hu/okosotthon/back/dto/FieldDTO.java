package hu.okosotthon.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {
    @Nullable
    private int fieldId;
    @Nullable
    private int deviceId;
    private String deviceName;
    private String fieldKey;
    private String fieldType;
}
