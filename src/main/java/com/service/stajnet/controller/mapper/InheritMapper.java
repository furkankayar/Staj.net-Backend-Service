package com.service.stajnet.controller.mapper;

import com.service.stajnet.dao.PersonalInformationDAO;
import com.service.stajnet.dao.RegisterDAO;
import com.service.stajnet.dto.UserDTO;
import com.service.stajnet.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InheritMapper {
    
    @InheritInverseConfiguration
    public UserDTO userEntityToDTO(User user);

    @InheritInverseConfiguration
    public User registerDAOToUserEntity(RegisterDAO registerDAO);

    @InheritInverseConfiguration
    public User updatePersonalInformation(@MappingTarget User user, PersonalInformationDAO personalInformation);
}