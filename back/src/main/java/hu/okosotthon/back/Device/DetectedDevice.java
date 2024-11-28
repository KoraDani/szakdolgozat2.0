package hu.okosotthon.back.Device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetectedDevice {
    private String deviceName = "";
    private List<GPIO> gpio = new ArrayList<>();
    private List<String> StatusSNS = new ArrayList<>();

    public void setGpio(GPIO gpio) {
        this.gpio.add(gpio);
    }

    public void setStatusSNS(String statusSNS) {
        this.StatusSNS.add(statusSNS);
    }



    @Override
    public String toString() {
        return "DetectedDevice{" +
                "deviceName='" + deviceName + '\'' +
                ", gpio=" + gpio +
                ", StatusSNS=" + StatusSNS +
                '}';
    }
}
