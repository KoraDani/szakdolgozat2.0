package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;


@Getter
@Setter
@Document("topics")
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @Nullable
    private String measurementId;
    private String username;
    private String topic;
    @Nullable
    private String payload;
//    @Nullable
//    private String dateTime;
}


