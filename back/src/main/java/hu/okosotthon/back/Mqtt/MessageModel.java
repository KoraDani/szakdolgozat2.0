package hu.okosotthon.back.Mqtt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageModel {
    private String prefix;
    private String postfix;
    private String msg;

    @Override
    public String toString() {
        return "MessageModel{" +
                "prefix='" + prefix + '\'' +
                ", postfix='" + postfix + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
