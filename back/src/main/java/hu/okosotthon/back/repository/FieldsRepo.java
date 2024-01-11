package hu.okosotthon.back.repository;

import hu.okosotthon.back.model.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FieldsRepo extends JpaRepository<Fields, Integer> {
    
}
