package hu.okosotthon.back.service;

import hu.okosotthon.back.dto.RegisterDTO;
import hu.okosotthon.back.exception.UserNotFoundException;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {
    private final UsersRepo usersRepo;
    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public Users addUser(Users users){
//        users.setId(UUID.randomUUID().node());
        return usersRepo.save(users);
    }

    public Users updateUsers(Users users){
        return usersRepo.save(users);
    }

    public Users findUsersById(int id)  {
        return usersRepo.findUsersById(id).orElseThrow(() -> new UserNotFoundException("User by id" + id+ " was noit found"));
    }

    public void deleteUser(Long id){
        usersRepo.deleteUsersById(id);
    }

    public Users findUsersByUsername(String username) {
        return usersRepo.findUsersByUsername(username);
    }

    public Users save(Users users) {
        return this.usersRepo.save(users);
    }

    public Users getUserByUsername(String username) {
        return this.usersRepo.findUsersByUsername(username);
    }
    public Users findUserByUsername(String username) {
        return this.usersRepo.getUsersByUsername(username);
    }

    public Users getUserByEmail(String email) {
        return this.usersRepo.findUsersByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users currentUser = this.usersRepo.findUsersByUsername(username);
        if(currentUser == null){
            throw new UsernameNotFoundException("Faild to find user with username: " + username);
        }

        return currentUser;
    }

    public Users getUserById(String userId) {
        return this.usersRepo.findUsersById(userId);
    }

    public List<Users> findAllUsers() {
        return this.usersRepo.findAll();
    }

    public Users saveUser(@Valid RegisterDTO registerDTO){
        return this.usersRepo.save(registerDTO.returnUser());
    }

    public Users changePassword(Users users) {
        return this.usersRepo.save(users);
    }


}
