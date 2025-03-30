package hu.okosotthon.back.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.okosotthon.back.Auth.UserDTO;
import hu.okosotthon.back.Auth.Users;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class UserAuthProvider {


    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTO user){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(user.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(validity)
                .withClaim("username",user.getUsername())
                .withClaim("role", user.getRole().name())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        Users user = Users.builder()
                .username(decoded.getClaim("username").asString())
                .build();
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
