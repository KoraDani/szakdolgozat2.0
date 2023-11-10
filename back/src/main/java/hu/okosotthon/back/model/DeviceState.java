package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;


@Document("deviceState")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceState {
    @Id
    @Nullable
    private String stateId;
    private int deviceId;
    private String timeStamp;
    @Nullable
    private int powerState;
    @Nullable
    private int brigthness;
    @Nullable
    private int temperature;

}
