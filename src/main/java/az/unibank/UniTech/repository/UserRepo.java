package az.unibank.UniTech.repository;

import az.unibank.UniTech.constant.Status;
import az.unibank.UniTech.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findByPincode(String pincode);

}