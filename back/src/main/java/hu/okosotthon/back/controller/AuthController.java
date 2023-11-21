package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.RegisterDTO;
import hu.okosotthon.back.dto.ResponseDTO;
import hu.okosotthon.back.dto.UserDTO;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.service.UsersService;
import hu.okosotthon.back.session.SessionRegistery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final UsersService usersService;
    private final AuthenticationManager manager;
    private final SessionRegistery sessionRegistery;

    @Autowired
    public AuthController(UsersService usersService, AuthenticationManager authenticationManager, SessionRegistery sessionRegistery) {
        this.usersService = usersService;
        this.manager = authenticationManager;
        this.sessionRegistery = sessionRegistery;
    }

//    @PostMapping("/login")
//        public ResponseEntity<Users> loginUsers(@RequestBody UserDTO userDTO) {
//        //TODO befejezni a logint
////        System.out.println(userDTO.toString());
//        Users users = usersService.getUserByEmail(userDTO.getEmail());
//        if (users.userEqual(userDTO)) {
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(users, HttpStatus.BAD_REQUEST);
//    }

//    {
//        "username":"valami",
//            "email":"valami@valamia.hu",
//            "password":"qweqwe"
//    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUsers(@RequestBody UserDTO userDTO) {
        this.manager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));

        final String sessionId = this.sessionRegistery.registerSession(userDTO.getUsername());

        ResponseDTO responseDTO = new ResponseDTO(sessionId,this.usersService.getUserByUsername(userDTO.getUsername()).getId(), userDTO.getUsername());

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/getUserByUsername")
    public ResponseEntity<Users> getUserById(@RequestBody String username){
        System.out.println(username);
//        Users user = this.usersService.getUserById(userId);
        Users user = this.usersService.findUsersByUsername(username);
//        System.out.println(user.getUsername());
        return new ResponseEntity<>(user,HttpStatus.OK);
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
        Users users = this.usersService.findUserByUsername(username);
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
}
