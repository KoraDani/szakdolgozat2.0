package hu.okosotthon.back.Mqtt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebSocModel {
    private String destination;
    private String listen;
    private String topic;
    private List<MessageModel> message;

    @Override
    public String toString() {
        return "WebSocModel{" +
                "destination='" + destination + '\'' +
                ", listen='" + listen + '\'' +
                ", topic='" + topic + '\'' +
                ", message=" + message +
                '}';
    }
}
