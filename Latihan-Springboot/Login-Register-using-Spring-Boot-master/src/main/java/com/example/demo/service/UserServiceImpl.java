package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
//        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public List<Object> isUserPresent(User user) {
        boolean userExists = false;
        String message = null;
        Optional<User> existingUserUsername = userRepository.findByUsername(user.getUsername());
        if(existingUserUsername.isPresent()){
            userExists = true;
            message = "Email Already Present!";
        }
        Optional<User> existingUserNama = userRepository.findByNama((String) user.getNama());
        if(existingUserNama.isPresent()){
            userExists = true;
            message = "Mobile Number Already Present!";
        }
        if (existingUserUsername.isPresent() && existingUserNama.isPresent()) {
            message = "Email and Mobile Number Both Already Present!";
        }
        System.out.println("existingUserEmail.isPresent() - "+existingUserUsername.isPresent()+"existingUserMobile.isPresent() - "+existingUserNama.isPresent());
        return Arrays.asList(userExists, message);
    }

    @Override
    public UserDetails loadUserByUsername(String nama) throws UsernameNotFoundException {
        return userRepository.findByNama(nama).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", nama)
                ));
    }
}
