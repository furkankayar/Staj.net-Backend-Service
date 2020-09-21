package com.service.stajnet.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.service.stajnet.model.ComputerSkill;
import com.service.stajnet.model.Contact;
import com.service.stajnet.model.EducationHistory;
import com.service.stajnet.model.ForeignLanguage;
import com.service.stajnet.model.JobHistory;
import com.service.stajnet.model.Project;
import com.service.stajnet.model.Social;
import com.service.stajnet.model.User.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private String nationality;

    private LocalDateTime birthdate;

    private Contact contact;

    private Set<Social> socials;

    private Set<ForeignLanguage> foreignLanguages;

    private Set<JobHistory> jobHistories;

    private Set<EducationHistory> educationHistories;

    private Set<Project> projects;

    private Set<ComputerSkill> computerSkills;
    
}