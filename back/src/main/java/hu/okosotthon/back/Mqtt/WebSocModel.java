package hu.okosotthon.back.Mqtt;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WebSocModel {
    private String destination;
    private String listen;
    private String topic;
    private List<MessageModel> message;

}
