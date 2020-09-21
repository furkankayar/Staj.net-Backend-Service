package com.service.stajnet.controller.mapper;

import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.UserProfileDTO;
import com.service.stajnet.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthenticationMapper {
    
    @InheritInverseConfiguration
    public UserProfileDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    public User registerDAOToUser(RegisterDAO registerDAO);

}