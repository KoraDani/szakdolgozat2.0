package hu.okosotthon.back.Auth;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String token;
    @Enumerated(EnumType.STRING)
    private Role role;

}
