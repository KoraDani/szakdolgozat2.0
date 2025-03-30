package hu.okosotthon.back.Auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.okosotthon.back.Device.Devices;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Users {
    /*{
        "id":0,
            "username":"",
            "email":"asd@asd.hu",
            "password":"asd",
            "imageUrl":"asd"
    }*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String email;
    private String password;
//    @NotBlank
    @Nullable
    private String imageUrl;
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    @Nullable
    private List<Devices> devicesList;
    @Nullable
    @Enumerated(EnumType.STRING)
    private Role role;

}
