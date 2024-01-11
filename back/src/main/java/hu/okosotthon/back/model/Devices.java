package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private int userId;
    private String topic;

    public Devices(String deviceName, String deviceType, String location, int userId, String topic) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.location = location;
        this.userId = userId;
        this.topic = topic;
    }
}
