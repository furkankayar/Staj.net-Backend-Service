package com.service.stajnet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.service.stajnet.controller.exception.NotAllowedException;
import com.service.stajnet.controller.exception.UserNotFoundException;
import com.service.stajnet.controller.mapper.InheritMapper;
import com.service.stajnet.dao.PersonalInformationDAO;
import com.service.stajnet.dao.UserDAO;
import com.service.stajnet.dto.UserDTO;
import com.service.stajnet.model.User;
import com.service.stajnet.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public User findByUsername(@PathVariable String username) {
        User entity = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(0l));
        return entity;
    }

    @PutMapping(value = "/{username}")
    public User updateUser(@PathVariable String username, @Valid @RequestBody PersonalInformationDAO personalInformationDAO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(principal instanceof UserDetails){
            String authUsername = ((UserDetails)principal).getUsername();
            if(authUsername.equals(username)){
                return userService.updateUser(personalInformationDAO, username);
            }
            else{
                throw new NotAllowedException();
            }
        }
        else{
            throw new NotAllowedException();
        }
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