package com.virtusdev.backend_practice.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserHttpClient {

    @GetExchange("/users")
    List<User> findAll();

    @GetExchange("/users/{id}")
    User findById(@PathVariable Integer id);

    @PostMapping("/users")
    void create(User user);

    @PutMapping("/users/{id}")
    void update(User user, Integer id);

    @DeleteMapping("/users/{id}")
    void delete(Integer id);

}
