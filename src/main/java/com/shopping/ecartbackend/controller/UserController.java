package com.shopping.ecartbackend.controller;


import com.shopping.ecartbackend.dao.UserRepository;
import com.shopping.ecartbackend.domain.UserModel;
import com.shopping.ecartbackend.model.User;
import com.shopping.ecartbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") int userId){
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAllUserObjects();
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserModel userModel){
        User userObject = userService.addUser(userModel);
        return new ResponseEntity<>(userObject,HttpStatus.CREATED);
    }

}
