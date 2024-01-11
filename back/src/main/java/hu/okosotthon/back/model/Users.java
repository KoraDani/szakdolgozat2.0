package hu.okosotthon.back.model;

import hu.okosotthon.back.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {
    /*{
        "id":0,
            "username":"",
            "email":"asd@asd.hu",
            "password":"asd",
            "imageUrl":"asd"
    }*/

    /**
     * Szóval itt egy olyan változtatás kell hogy a usernél lesz eltárolni a topicok amikre feliratkozik
     * minden bejelentkezésnél feliratkozik a topicocra és így meg van oldva hogy ha a user új
     * eszközt add hozzá akkor automatikus feliratkozonn egy topicra
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
//    @NotBlank
    private String imageUrl;


    @Override
    public String toString() {
        return "Users{" +
                "id=" + userId +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public boolean userEqual(UserDTO userDTO) {
        return this.email.equals(userDTO.getEmail()) /*&& this.username.equals(userDTO.getUsername())*/ && this.password.equals(userDTO.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
