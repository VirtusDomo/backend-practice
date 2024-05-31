package com.virtusdev.backend_practice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserHttpClient userHttpClient;

    @Autowired
    public UserService(UserHttpClient userHttpClient){
        this.userHttpClient = userHttpClient;
    }

    public List<User> getAllUsers(){
        return userHttpClient.findAll();
    }

    public User getUserById(Integer id){
        return userHttpClient.findById(id);
    }

    public void createUser(User user){
        userHttpClient.create(user);
    }

    public void updateUser(User user, Integer id){
        userHttpClient.update(user, id);
    }

    public void deleteUser(Integer id){
        userHttpClient.delete(id);
    }

}
