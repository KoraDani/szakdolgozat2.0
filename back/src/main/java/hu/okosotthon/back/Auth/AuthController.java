package hu.okosotthon.back.Auth;

import hu.okosotthon.back.session.SessionRegistery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private SessionRegistery sessionRegistery;

    public static UserResponseDTO currentUserRes;
    public static Users currentUser;


    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUsers(@RequestBody UserDTO userDTO) {
        System.out.println("Felhasználó beléptetése "+ userDTO.getUsername()+ " " + userDTO.getPassword());

        this.manager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));

        final String sessionId = this.sessionRegistery.registerSession(userDTO.getUsername());
        System.out.println("sessionId: " + sessionId);

        //Bejelentkezett user elmentése a bakcendben
        currentUser = this.usersService.findUsersByUsername(userDTO.getUsername());
        currentUserRes = convertUser(currentUser);
        currentUserRes.setSessionId(sessionId);
        return ResponseEntity.ok(currentUserRes);
    }

    //TODO megkérdezni hogy jó ötlet e eltárolni a usert backendnél
    @PostMapping("/getUserByUsername")
    public ResponseEntity<UserResponseDTO> getUserById(){
        return new ResponseEntity<>(currentUserRes,HttpStatus.OK);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<Users> saveUser(@Valid @RequestBody RegisterDTO registerDTO) {
        System.out.println(registerDTO.toString());
        if(registerDTO.getPassword1().equals(registerDTO.getPassword2())){
            registerDTO.setPassword1(new BCryptPasswordEncoder().encode(registerDTO.getPassword1()));
            Users newUser = this.usersService.saveUser(registerDTO);

            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Users(), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<Users> changePassword(@RequestParam String username,@RequestParam String oldpwd,@RequestParam String newpwd1,@RequestParam String newpwd2){
//        System.out.println("Jleszó változtatás"+ oldpwd);
        Users users = this.usersService.findUsersByUsername(username);
        System.out.println(users.toString());
        System.out.println(hasPassword(newpwd1));
        if(samePassowrd(oldpwd,users.getPassword())){
            if(newpwd1.equals(newpwd2)){
                users.setPassword(hasPassword(newpwd1));
                users = this.usersService.save(users);
            }
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    public String hasPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
    public boolean samePassowrd(String oldpwd, String hashPwd){
        return new BCryptPasswordEncoder().matches(oldpwd, hashPwd);
    }
    public UserResponseDTO convertUser(Users users){
        return new UserResponseDTO(users.getUserId(), users.getUsername(), users.getEmail(), users.getImageUrl());
    }
}
