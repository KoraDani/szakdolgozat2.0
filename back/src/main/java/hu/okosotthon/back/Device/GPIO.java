package hu.okosotthon.back.Device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GPIO {
    private String gpio;
    private String number;
    private String name;

    @Override
    public String toString() {
        return "GPIO{" +
                "gpio='" + gpio + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
