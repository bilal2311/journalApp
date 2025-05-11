package org.billz.journalapp.controller;

import org.billz.journalapp.api.response.WeatherResponse;
import org.billz.journalapp.entity.User;
import org.billz.journalapp.repository.UserRepository;
import org.billz.journalapp.service.UserService;
import org.billz.journalapp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    WeatherService weatherService;


    @GetMapping("id/{myId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId myId){
      Optional<User>user=userService.findById(myId);
      if(user.isPresent()){
          return new ResponseEntity<>(user.get(), HttpStatus.OK);
      }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User userInDB=userService.findByUserName(userName);
        if (userInDB == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        //userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteUserByName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userRepository.deleteByUserName(auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("Canada");
        String greeting="";
        if(weatherResponse!=null){
            greeting=", weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " +authentication.getName() +greeting,HttpStatus.OK);
    }
}
