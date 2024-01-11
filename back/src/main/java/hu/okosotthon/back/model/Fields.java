package hu.okosotthon.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fieldId;
    private int deviceId;
    private String fieldKey;
    private String fieldType;

    public Fields(int deviceId, String fieldKey, String fieldType) {
        this.deviceId = deviceId;
        this.fieldKey = fieldKey;
        this.fieldType = fieldType;
    }
}
