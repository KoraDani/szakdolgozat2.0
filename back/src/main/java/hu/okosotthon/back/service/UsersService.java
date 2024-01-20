package hu.okosotthon.back.service;

import hu.okosotthon.back.dto.RegisterDTO;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

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

    public Users findUsersByUsername(String username) {
        return usersRepo.findUsersByUsername(username);
    }

    public Users save(Users users) {
        return this.usersRepo.save(users);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users currentUser = this.usersRepo.findUsersByUsername(username);
        if(currentUser == null){
            throw new UsernameNotFoundException("Faild to find user with username: " + username);
        }
        return currentUser;
    }

    public Users saveUser(@Valid RegisterDTO registerDTO){
        return this.usersRepo.save(registerDTO.returnUser());
    }


//    public List<String> getSubscribedTopicsFromUser(){
//        return AuthController.currentUser;
//    }


    public void updateUsersById(Users currentUser, String topic) {
//        currentUser.setSubscribedTopic(topic);
        this.usersRepo.save(currentUser);
    }
}
