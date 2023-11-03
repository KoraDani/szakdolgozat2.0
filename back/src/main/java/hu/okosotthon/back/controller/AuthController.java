package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.UserDTO;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final UsersService usersService;

    @Autowired
    public AuthController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<Users> loginUsers(@RequestBody UserDTO userDTO) {
        //TODO befejezni a logint
//        System.out.println(userDTO.toString());
        Users users = usersService.getUserByEmail(userDTO.getEmail());
        if (users.userEqual(userDTO)) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(users, HttpStatus.BAD_REQUEST);
    }
}
