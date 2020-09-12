package com.service.stajnet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.service.stajnet.controller.exception.UserNotFoundException;
import com.service.stajnet.controller.mapper.InheritMapper;
import com.service.stajnet.dao.UserDAO;
import com.service.stajnet.dto.UserDTO;
import com.service.stajnet.model.User;
import com.service.stajnet.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private InheritMapper mapper;

    @GetMapping(value = "/{username}")
    public User findByUsername(@PathVariable String username){
        User entity = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(0l));
        return entity; 
    }

    @GetMapping
    public Collection<UserDTO> findAll(){
        Iterable<User> users = this.userService.findAll();
        List<UserDTO> userList = new ArrayList<UserDTO>();
        users.forEach(user -> userList.add(mapper.userEntityToDTO(user)));
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