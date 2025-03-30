package hu.okosotthon.back.Auth;

import hu.okosotthon.back.config.UserAuthProvider;
import jakarta.validation.Valid;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private UsersService usersService;
    public static Users currentUser;
    @Autowired
    private UserAuthProvider userAuthProvider;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUsers(@RequestBody Users user) throws Exception {

        UserDTO userDTO = this.usersService.loginUsersByUsername(user);

        userDTO.setToken(userAuthProvider.createToken(userDTO));

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<UserDTO> saveUser(@RequestBody Users user) {
        UserDTO userDTO = this.usersService.save(user);
        userDTO.setToken(userAuthProvider.createToken(userDTO));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Users> changePassword(@RequestParam String username,@RequestParam String oldpwd,@RequestParam String newpwd1,@RequestParam String newpwd2){

        return new ResponseEntity<>(this.usersService.changePassword(username, oldpwd,newpwd1, newpwd2),HttpStatus.OK);
    }
}
