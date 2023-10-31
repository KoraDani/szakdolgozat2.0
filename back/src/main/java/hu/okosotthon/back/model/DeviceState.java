package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeviceState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stateId;
    private int deviceId;
    private String timeStamp;
    @Nullable
    private int powerState;
    @Nullable
    private int brigthness;
    @Nullable
    private int temperature;

    public DeviceState() {
    }

    public DeviceState(int stateId, int deviceId, String timeStamp, int powerState, int brigthness, int temperature) {
        this.stateId = stateId;
        this.deviceId = deviceId;
        this.timeStamp = timeStamp;
        this.powerState = powerState;
        this.brigthness = brigthness;
        this.temperature = temperature;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPowerState() {
        return powerState;
    }

    public void setPowerState(int powerState) {
        this.powerState = powerState;
    }

    public int getBrigthness() {
        return brigthness;
    }

    public void setBrigthness(int brigthness) {
        this.brigthness = brigthness;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
