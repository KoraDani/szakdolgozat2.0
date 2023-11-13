package hu.okosotthon.back.controller;

import hu.okosotthon.back.dto.ResponseDTO;
import hu.okosotthon.back.dto.UserDTO;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.service.UsersService;
import hu.okosotthon.back.session.SessionRegistery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUsers(@RequestBody UserDTO userDTO) {
        this.manager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));

        final String sessionId = this.sessionRegistery.registerSession(userDTO.getUsername());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSessionId(sessionId);

        return ResponseEntity.ok(responseDTO);
    }
}
