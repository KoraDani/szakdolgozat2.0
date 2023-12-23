package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document("devices")
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
    @Nullable
    private String devicesId;
//    @NotBlank(message = "Minden mezőt ki kell tölteni")
    private String deviceName;
//    @NotBlank(message = "Minden mezőt ki kell tölteni")
    private String deviceType;
//    @NotBlank(message = "Minden mezőt ki kell tölteni")
    private String location;
    //TODO: valszeg nem lesz jó MySQL  ehhez a projekthez mert nem tudok itt atomi filedet csinálni
    private List<String> params = new ArrayList<>();
    private String userId;
    private String topic;

}
