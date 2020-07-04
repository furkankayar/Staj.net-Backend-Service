package com.service.stajnet.dto;

import java.util.Set;

import com.service.stajnet.model.Role;
import com.service.stajnet.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    
    private String username;
    private String firstName;
    private String lastName;
    private User.Gender gender;
    private Set<Role> roles;
    private boolean enabled;
    private boolean accountNonLocked;
}