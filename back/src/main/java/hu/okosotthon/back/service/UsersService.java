package hu.okosotthon.back.service;

import hu.okosotthon.back.exception.UserNotFoundException;
import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {
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
}
