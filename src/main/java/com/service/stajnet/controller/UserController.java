package com.service.stajnet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.service.stajnet.controller.exception.NotAllowedException;
import com.service.stajnet.controller.mapper.AuthenticationMapper;
import com.service.stajnet.dao.ContactInformationDAO;
import com.service.stajnet.dao.PersonalInformationDAO;
import com.service.stajnet.dao.UserDAO;
import com.service.stajnet.dto.UserProfileDTO;
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
    private AuthenticationMapper mapper;

    @GetMapping(value = "/{username}")
    public UserProfileDTO findProfileByUsername(@PathVariable String username) {
        return userService.findProfileByUsername(username);
    }

    @PutMapping(value = "/{username}/personalInformation")
    public UserProfileDTO updateUser(@PathVariable String username, @Valid @RequestBody PersonalInformationDAO personalInformationDAO) {
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

    @PutMapping(value = "/{username}/contactInformation")
    public UserProfileDTO updateUser(@PathVariable String username, @Valid @RequestBody ContactInformationDAO contactInformationDAO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            String authUsername = ((UserDetails)principal).getUsername();
            if(authUsername.equals(username)){
                return userService.updateUser(contactInformationDAO, username);
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
    public Collection<UserProfileDTO> findAll(){
        Iterable<User> users = this.userService.findAll();
        List<UserProfileDTO> userList = new ArrayList<UserProfileDTO>();
        users.forEach(user -> userList.add(mapper.userToUserDTO(user)));
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