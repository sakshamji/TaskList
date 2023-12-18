package com.projectLearn.TaskList.service;

import com.projectLearn.TaskList.entites.Token;
import com.projectLearn.TaskList.entites.User;
import com.projectLearn.TaskList.exceptions.AlreadyExistException;
import com.projectLearn.TaskList.model.JwtRequest;
import com.projectLearn.TaskList.model.JwtResponse;
import com.projectLearn.TaskList.repository.TokenRepository;
import com.projectLearn.TaskList.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserService userService;

    private void doAuthenticate(String email, String password) {
        //System.out.println("passsword is "+password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid Username or Password!!!");
        }
    }
    public JwtResponse register(User user) throws AlreadyExistException {
        if(userService.doesUserExist(user))
        {
            throw new AlreadyExistException("User already exist");
        }
        String password = user.getPassword();
        userService.createUser(user);
        this.doAuthenticate(user.getEmail(),password);

        UserDetails loadedUser = userDetailsService.loadUserByUsername(user.getEmail());
        String token = this.helper.generateToken(loadedUser);
        Token token1 = Token.builder()
                .user(user)
                .token(token)
                .build();
        tokenRepository.save(token1);
        return JwtResponse.builder().jwtToken(token).userName(loadedUser.getUsername()).build();
    }

    public JwtResponse login(JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        Optional<User> foundUser = userService.findUser(request.getEmail());
        //System.out.println("exected found user and got "+ foundUser.get());
        // find token based on the user email id
        Token oldToken = tokenRepository.findTokenByUser(foundUser.get().getUserId());
        //System.out.println("exected found token and got "+oldToken);

        //delete old token associated with  passed email
        if(oldToken!=null){
            tokenRepository.delete(oldToken);
        }

        //System.out.println("deleted old token ");

        String token = this.helper.generateToken(user);
        Token token1 = Token.builder()
                .user(foundUser.get())
                .token(token)
                .build();
        //System.out.println("new token to save "+token1);

        tokenRepository.save(token1);
        //System.out.println("token saved");

        return JwtResponse.builder().jwtToken(token).userName(user.getUsername()).build();
    }
}
