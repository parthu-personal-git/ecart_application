package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.UserRepository;
import com.shopping.ecartbackend.domain.UserModel;
import com.shopping.ecartbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(int userId){
        User user = userRepository.findById(userId).get();
        return user;
    }

    public List<User> getAllUserObjects(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User addUser(UserModel userModel){
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(userModel.getPassword());
        userRepository.save(user);
        return user;
    }
}
