package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Document("topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topics {
    @Id
    @Nullable
    private String topicId;
    private String topic;
    @Nullable
    private JSONObject payload;
}
