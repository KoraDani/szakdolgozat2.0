package hu.okosotthon.back.service;

import hu.okosotthon.back.controller.AuthController;
import hu.okosotthon.back.model.Fields;
import hu.okosotthon.back.repository.FieldsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldsService {

    private FieldsRepo fieldsRepo;

    @Autowired
    public FieldsService(FieldsRepo fieldsRepo) {
        this.fieldsRepo = fieldsRepo;
    }

    public List<Fields> saveAll(List<Fields> fields) {
        return this.fieldsRepo.saveAll(fields);
    }

    public List<Fields> getAllFieldsByUserId() {
        return this.fieldsRepo.getAllByUserId(AuthController.currentUser.getUserId());
    }

    public Fields save(Fields fields) {
        return this.fieldsRepo.save(fields);
    }
}
