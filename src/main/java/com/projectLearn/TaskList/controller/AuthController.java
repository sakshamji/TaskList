package com.projectLearn.TaskList.controller;

import com.projectLearn.TaskList.entites.User;
import com.projectLearn.TaskList.exceptions.AlreadyExistException;
import com.projectLearn.TaskList.model.JwtRequest;
import com.projectLearn.TaskList.model.JwtResponse;
import com.projectLearn.TaskList.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    //private Logger logger = (Logger) LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        JwtResponse response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/register")
    public ResponseEntity<JwtResponse> createUser(@RequestBody User user) throws AlreadyExistException {
        JwtResponse response = authService.register(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
