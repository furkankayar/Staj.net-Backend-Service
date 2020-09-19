package com.service.stajnet.service;

import java.util.Optional;

import com.service.stajnet.controller.mapper.InheritMapper;
import com.service.stajnet.dao.PersonalInformationDAO;
import com.service.stajnet.model.User;
import com.service.stajnet.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private InheritMapper mapper;
    
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public User updateUser(PersonalInformationDAO data, String username){
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
        System.out.println(user);
        user = mapper.updatePersonalInformation(user, data);
        System.out.println(user);
        return this.userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        return byUsername.orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
    }
}