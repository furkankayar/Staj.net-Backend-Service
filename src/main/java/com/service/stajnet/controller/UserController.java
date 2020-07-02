package com.service.stajnet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.service.stajnet.controller.exception.NotProperIdException;
import com.service.stajnet.controller.exception.UserNotFoundException;
import com.service.stajnet.dao.UserDAO;
import com.service.stajnet.model.User;
import com.service.stajnet.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/{user_id}")
    public User findOne(@PathVariable String user_id){
        final Long id;
        try{
            id = Long.parseLong(user_id);
        }
        catch(NumberFormatException ex){
            throw new NotProperIdException(user_id);
        }

        User entity = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return entity;
    }

    @GetMapping
    public Collection<User> findAll(){
        Iterable<User> users = this.userService.findAll();
        List<User> userList = new ArrayList<User>();
        users.forEach(user -> userList.add(user));
        return userList;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User newUser(@Valid @RequestBody UserDAO userDao){
        
        return this.userService.save(
            User.builder()
                .username(userDao.getUsername())
                .firstName(userDao.getFirstName())
                .lastName(userDao.getLastName())
                .build()
        );
    }
}