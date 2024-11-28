package hu.okosotthon.back.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String sessionId;
    private int userId;
    private String username;
    private String email;
    private String imageUrl;

    public UserResponseDTO(String sessionId, int userId, String username) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.username = username;
    }

    public UserResponseDTO(int userId, String username, String email, String imageUrl) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
