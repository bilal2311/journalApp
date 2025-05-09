package org.billz.journalapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.billz.journalapp.entity.User;
import org.billz.journalapp.service.UserDetailServiceImp;
import org.billz.journalapp.service.UserService;
import org.billz.journalapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailServiceImp userDetailServiceImp;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String HealthCheck(){
        return "ok!";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user){
        try{
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (user.getUserName(),user.getPassword()));
           UserDetails userDetails= userDetailServiceImp.loadUserByUsername(user.getUserName());
          String jwt= jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Exception occured while createAuthentication",e);
            return new ResponseEntity<>("Incorrect Username or Password",HttpStatus.BAD_REQUEST);
        }
    }
}
