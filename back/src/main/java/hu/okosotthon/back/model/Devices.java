package hu.okosotthon.back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int devicesId;
    private String deviceName;
    private String deviceType;
    private String location;
    private int userId;

    public Devices() {
    }

    public Devices(int devicesId, String deviceName, String deviceType, String location, int userId) {
        this.devicesId = devicesId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.location = location;
        this.userId = userId;
    }

    public int getDevicesId() {
        return devicesId;
    }

    public void setDevicesId(int devicesId) {
        this.devicesId = devicesId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
