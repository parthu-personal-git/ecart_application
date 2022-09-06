package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.UserRepository;
import com.shopping.ecartbackend.domain.UserModel;
import com.shopping.ecartbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    static Logger logger = Logger.getLogger(String.valueOf(UserService.class));

    @Autowired
    private UserRepository userRepository;

    public User getUserById(int userId){
        logger.info("starting the UserService:getUserById service");
        User user = userRepository.findById(userId).get();
        logger.info("ending the UserService:getUserById service");
        return user;
    }

    public List<User> getAllUserObjects(){
        logger.info("starting the UserService:getAllUserObjects service");
        List<User> userList = userRepository.findAll();
        logger.info("ending the UserService:getAllUserObjects service");
        return userList;
    }

    public User addUser(UserModel userModel){
        logger.info("starting the UserService:addUser service");
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(userModel.getPassword());
        userRepository.save(user);
        logger.info("ending the UserService:addUser service");
        return user;
    }
}
