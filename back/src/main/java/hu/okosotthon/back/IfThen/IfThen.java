package hu.okosotthon.back.IfThen;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IfThen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ifThenId;
    private int ifDeviceId;
    private int thenDeviceId;
    private int ifFieldId;
    private String ifValue;
    private int thenFieldId;
    private String after;
    private String message;
    private String whenToCheck;
    private String time;


    @Override
    public String toString() {
        return "IfThen{" +
                "ifThenId=" + ifThenId +
                ", ifDeviceId=" + ifDeviceId +
                ", thenDeviceId=" + thenDeviceId +
                ", ifFieldId=" + ifFieldId +
                ", ifValue='" + ifValue + '\'' +
                ", thenFieldId=" + thenFieldId +
                ", after='" + after + '\'' +
                ", message='" + message + '\'' +
                ", whenToCheck='" + whenToCheck + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
