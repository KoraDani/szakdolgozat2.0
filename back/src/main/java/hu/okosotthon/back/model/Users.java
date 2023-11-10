package hu.okosotthon.back.model;

import hu.okosotthon.back.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {
    /*{
        "id":0,
            "username":"",
            "email":"asd@asd.hu",
            "password":"asd",
            "imageUrl":"asd"
    }*/


    /**
     * Serializable segít transzformálni a külömböző osztályokat külömbőző streamre konvertálni
     * */
    @Id
    @Nullable
    private String id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
//    @NotBlank
    private String imageUrl;

//    public Users() {
//    }

//    public Users(int id, String username, String email, String password, String imageUrl) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.imageUrl = imageUrl;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setUsername(String name) {
//        this.username = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public boolean userEqual(UserDTO userDTO) {
        return this.email.equals(userDTO.getEmail()) /*&& this.username.equals(userDTO.getUsername())*/ && this.password.equals(userDTO.getPassword());
    }
}
