package hu.okosotthon.back.Auth;

import hu.okosotthon.back.Auth.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

}
