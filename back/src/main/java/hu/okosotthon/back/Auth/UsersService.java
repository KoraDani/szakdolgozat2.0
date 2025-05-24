package hu.okosotthon.back.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;


@Service
public class UsersService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UsersService(UsersRepo usersRepo,PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDTO loginUserByEmail(Users users) throws Exception {
        Users u = usersRepo.findUsersByUsername(users.getUsername());
        if(passwordEncoder.matches(CharBuffer.wrap(users.getPassword()), u.getPassword())){
            return new UserDTO(u.getUserId(), u.getUsername(), u.getEmail(),"", u.getRole());
        }
        throw new Exception("No user found");
    }

    public UserDTO save(Users users) {
        users.setPassword(passwordEncoder.encode(CharBuffer.wrap(users.getPassword())));
        Users u = this.usersRepo.save(users);
        return new UserDTO(u.getUserId(), u.getUsername(), u.getEmail(), "", u.getRole());
    }


    public Users changePassword(String username, String oldpwd, String newpwd1, String newpwd2) {
        Users u = this.usersRepo.findUsersByUsername(username);

        if(passwordEncoder.matches(CharBuffer.wrap(u.getPassword()), oldpwd)){
            if (newpwd1.equals(newpwd2)){
                u.setPassword(passwordEncoder.encode(CharBuffer.wrap(newpwd1)));
            }
        }
        return this.usersRepo.save(u);
    }

    public UserDTO getCurrentUser(int userId) {
        Users users = this.usersRepo.findUsersByUserId(userId);
        return new UserDTO(users.getUserId(), users.getUsername(), users.getEmail(), "", users.getRole());
    }
}
