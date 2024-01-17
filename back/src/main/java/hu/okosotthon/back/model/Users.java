package hu.okosotthon.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Devices> devicesList;

    public Users(int userId, String username, String email, String password, String imageUrl) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
    }

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
