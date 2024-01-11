package hu.okosotthon.back.dto;

import hu.okosotthon.back.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


//        {
//        "username":"proba",
//        "email":"asd@äsd.hu",
//        "password1":"qweqwe",
//        "password2":"qweqwe",
//        "imageUrl":"asd"
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
        return new Users(0,this.username,this.email,this.password1,this.imageUrl);
    }
}
