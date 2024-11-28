package hu.okosotthon.back.NotUsing;

import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;



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
