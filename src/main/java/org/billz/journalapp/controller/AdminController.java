package org.billz.journalapp.controller;

import org.billz.journalapp.cache.AppCache;
import org.billz.journalapp.entity.User;
import org.billz.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    AppCache appCache;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser(){
       List<User> all= userService.getAll();
       if(all != null && !all.isEmpty()){
           return new ResponseEntity<>(all, HttpStatus.OK);
       }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user) {
    userService.saveAdmin(user);
    }

   @GetMapping("/clear-app-cache")
   public void clearAppCache(){
        appCache.init();
    }


}
