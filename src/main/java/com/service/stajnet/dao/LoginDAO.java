package com.service.stajnet.dao;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDAO {
    
    @NotBlank(message = "Name is mandotary")
    private String username;
    @NotBlank(message = "Password is mandotary")
    private String password;
}