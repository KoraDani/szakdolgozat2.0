package hu.okosotthon.back.dto;

import hu.okosotthon.back.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


//{
//        "username":"valami",
//        "email":"valami@valamia.hu",
//        "password1":"asdasd",
//        "password2":"asdasd",
//        "imageUrl":"valami"
//        }
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String username;
    private String email;
    private String password1;
    private String password2;
    private String imageUrl;
    public Users returnUser(){
        return new Users(null,this.username,this.email,this.password1,this.imageUrl);
    }
}
