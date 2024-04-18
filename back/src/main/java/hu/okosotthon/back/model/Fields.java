package hu.okosotthon.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fieldId;
//    private int deviceId;
    private String fieldKey;
    private String fieldType;
    @ManyToOne
    @JoinColumn(name = "devicesId")
    @JsonBackReference
    private Devices devices;

    public Fields(String fieldKey, String fieldType) {
        this.fieldKey = fieldKey;
        this.fieldType = fieldType;
    }

    public Fields(String fieldKey, String fieldType, Devices devices) {
        this.fieldKey = fieldKey;
        this.fieldType = fieldType;
        this.devices = devices;
    }
    //    public Fields(int deviceId, String fieldKey, String fieldType) {
//        this.deviceId = deviceId;
//        this.fieldKey = fieldKey;
//        this.fieldType = fieldType;
//    }


    @Override
    public String toString() {
        return "{" +
                "fieldId=" + fieldId +
                ", fieldKey='" + fieldKey + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", devices=" + devices.getDevicesId() +
                '}';
    }

    public String fieldTypeToString(){
        return fieldKey+":"+fieldType;
    }
}
