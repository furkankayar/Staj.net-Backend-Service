package com.service.stajnet.repository;

import java.util.Optional;

import com.service.stajnet.model.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends PagingAndSortingRepository<User, Long>{
    
    public Optional<User> findByUsername(@Param("username") String username);
}