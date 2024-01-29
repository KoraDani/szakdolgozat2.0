package hu.okosotthon.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.okosotthon.back.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Devices {

    /*{
        "devicesId": 0,
            "deviceName":"",
            "deviceType": "asd",
            "location": "asd",
            "userId": 1
    }*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devicesId;
    //    @NotBlank(message = "Minden mezőt ki kell tölteni")
    private String deviceName;
    //    @NotBlank(message = "Minden mezőt ki kell tölteni")
    private String deviceType;
    //    @NotBlank(message = "Minden mezőt ki kell tölteni")
    private String location;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonManagedReference
    private Users users;
    @Nullable
    @OneToMany(mappedBy = "devices", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Fields> fieldsList;
    @Nullable
    @OneToMany(mappedBy = "devices", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Measurement> measurementList;
    //    private int userId;
    private String topic;
    private int active;

    public Devices(String deviceName, String deviceType, String location, Users users, String topic) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.location = location;
        this.users = users;
        this.topic = topic;
    }

    public Devices(String deviceName, String deviceType, String location, Users users, List<Fields> fieldsList, String topic) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.location = location;
        this.users = users;
        this.fieldsList = fieldsList;
        this.topic = topic;
    }
    //    public Devices(String deviceName, String deviceType, String location, int userId, String topic) {
//        this.deviceName = deviceName;
//        this.deviceType = deviceType;
//        this.location = location;
//        this.userId = userId;
//        this.topic = topic;
//    }

    public DeviceDTO convertDivece() {
        String[] fieldKey = new String[fieldsList.size()];
        String[] fieldType = new String[fieldsList.size()];
        String[] payloadKey = new String[measurementList.size() == 0 ? fieldsList.size() : measurementList.size()];
        String[] payloadValue = new String[measurementList.size() == 0 ? fieldsList.size() : measurementList.size()];

        for (int i = 0; i < fieldsList.size(); i++) {
            if(!measurementList.isEmpty()){
                if (fieldsList.get(i).getFieldKey().equals(measurementList.get((measurementList.size()-2)-i).getPayloadKey())) {
                    fieldKey[i] = fieldsList.get(i).getFieldKey();
                    fieldType[i] = fieldsList.get(i).getFieldType();
                }
            }else {
                System.out.println(fieldsList.get(i).getFieldKey());
                fieldKey[i] = fieldsList.get(i).getFieldKey();
                fieldType[i] = fieldsList.get(i).getFieldType();
                payloadKey[i] = "0";
                payloadValue[i] ="0";
            }
        }
        if(!measurementList.isEmpty()){
            for (int i = 0; i < measurementList.size(); i++){
                payloadKey[i] = measurementList.get(i).getPayloadKey();
                payloadValue[i] = measurementList.get(i).getPayloadValue();
            }
        }

        return new DeviceDTO(
                devicesId,
                deviceName,
                deviceType,
                location,
                topic,
                active,
                fieldKey,
                fieldType,
                payloadKey,
                payloadValue);
    }
}
