package hu.okosotthon.back.IfThen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IfThenDTO {
    private int ifThenId;
    private int ifDeviceId;
    private String ifDeviceName;
    private int thenDeviceId;
    private String thenDeviceName;
    private int ifFieldId;
    private String ifValue;
    private String ifFieldName;
    private int thenFieldId;
    private String thenFieldName;
    private String after;
    private String message;
    private String whenToCheck;
    private String time;
}
