package com.projectLearn.TaskList.service;

import com.projectLearn.TaskList.repository.UserRepository;
import com.projectLearn.TaskList.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void createUser(User user){
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
    }

    public boolean doesUserExist(User user){
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        return foundUser.isPresent();
    }

    public Optional<User> findUser(String email){

        return userRepository.findByEmail(email);
    }

}
