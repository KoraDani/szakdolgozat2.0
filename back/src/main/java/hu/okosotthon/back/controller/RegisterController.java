package hu.okosotthon.back.controller;

import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("saveUser")
    public ResponseEntity<Users> saveUser(@RequestBody Users user){
        System.out.println(user.toString());
        if(user.getUsername() != "" && user.getEmail() != "" && user.getPassword() != ""){
            Users newUser = this.registerService.saveUser(user);

            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }
}
