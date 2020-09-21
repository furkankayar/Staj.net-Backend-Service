package com.service.stajnet.dao;

import java.time.LocalDateTime;

import com.service.stajnet.model.User.Gender;
import com.service.stajnet.model.User.Nationality;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInformationDAO {
    
    @Length(max = 20, message = "First name must be shorter than 20 characters")
    private String firstName;

    @Length(max = 20, message = "Last name must be shorter than 20 characters")
    private String lastName;

    private LocalDateTime birthdate;

    private Gender gender;

    private Nationality nationality;
}