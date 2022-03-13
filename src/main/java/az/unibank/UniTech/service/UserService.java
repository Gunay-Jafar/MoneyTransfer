package az.unibank.UniTech.service;

import az.unibank.UniTech.model.User;
import az.unibank.UniTech.dto.UserDto;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findOne(String username);

    User save(UserDto user);

}
