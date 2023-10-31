package hu.okosotthon.back.service;

import hu.okosotthon.back.model.Users;
import hu.okosotthon.back.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private RegisterRepo registerRepo;

    @Autowired
    public RegisterService(RegisterRepo registerRepo) {
        this.registerRepo = registerRepo;
    }

    public Users saveUser(Users users){
        return this.registerRepo.save(users);
    }
}
