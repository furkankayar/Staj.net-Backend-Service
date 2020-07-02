package com.service.stajnet.dao;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDAO {
    
    @NotNull
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}