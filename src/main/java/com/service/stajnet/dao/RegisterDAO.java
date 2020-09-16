package com.service.stajnet.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.service.stajnet.model.Social;
import com.service.stajnet.model.User.Gender;
import com.service.stajnet.model.User.Nationality;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDAO {

    @NotBlank(message = "Username is mandotary!")
    @Length(min = 6, message = "Username must be at least 6 characters")
    @Length(max = 15, message = "Username must be shorter than 15 characters")
    private String username;
    
    @NotBlank(message = "First name is mandotary!")
    @Length(max = 20, message = "First name must be shorter than 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandotary!")
    @Length(max = 20, message = "Last name must be shorter than 20 characters")
    private String lastName;

    @NotBlank(message = "Email is mandotary!")
    @Email(message = "Email pattern is invalid!")
    private String email;

    @Nullable
    private Gender gender;

    @NotBlank(message = "Password is mandotary!")
    @Length(min = 8, message = "Password must be at least 8 characters!")
    @Length(max = 15, message = "Password must be shorter than 15 characters!")
    private String password;

    @Nullable
    private Date birthdate;

    @Nullable
    private Nationality nationality;
    
    
    @Nullable
    @Builder.Default
    private Set<Social> socials = new HashSet<Social>(); 
}