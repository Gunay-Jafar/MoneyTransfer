package az.unibank.UniTech.service.impl;

import az.unibank.UniTech.constant.Status;
import az.unibank.UniTech.error.CustomIllegalArgumentException;
import az.unibank.UniTech.repository.UserRepo;
import az.unibank.UniTech.model.User;
import az.unibank.UniTech.dto.UserDto;
import az.unibank.UniTech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String pincode) throws UsernameNotFoundException {
        User user = userRepo.findByPincode(pincode);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid pinCode or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getPincode(), user.getPassword(), getAuthority());
    }

    private Set<SimpleGrantedAuthority> getAuthority() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String pinCode) {
        return userRepo.findByPincode(pinCode);
    }

    @Override
    public User save(UserDto user) {
        User userForPin = userRepo.findByPincode(user.getPincode());
        User nUser = user.getUserFromDto();
        if (userForPin == null) {
            nUser.setPincode(user.getPincode());
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        } else {
            throw new CustomIllegalArgumentException("Registered pin was exist !");
        }
        return userRepo.save(nUser);
    }

}
