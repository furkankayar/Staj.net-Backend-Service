package com.service.stajnet.controller.mapper;

import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.UserDTO;
import com.service.stajnet.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InheritMapper {
    
    @InheritInverseConfiguration
    public UserDTO userEntityToDTO(User user);

    @InheritInverseConfiguration
    public User registerDAOToUserEntity(RegisterDAO registerDAO);
}