package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private int thenFieldId;
    private String after;
    private String message;
    

    @Override
    public String toString() {
        return "IfThen{" +
                "ifThenId=" + ifThenId +
                ", ifDeviceId=" + ifDeviceId +
                ", thenDeviceId=" + thenDeviceId +
                ", ifFieldId=" + ifFieldId +
                ", thenFieldId=" + thenFieldId +
                ", after='" + after + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
