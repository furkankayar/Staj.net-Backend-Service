package com.service.stajnet.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
public class Company {
    
    public enum Type{
        PUBLIC,
        PRIVATE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="type", nullable=false)
    private Type type;

    @Column(name="founded_at", nullable=false)
    private Timestamp foundedAt; 

    @Column(name = "central_office", nullable=true)
    private String centralOffice;

    @Column(name = "business_sector", nullable=false)
    private String businessSector;

    @Column(name = "about", nullable=false)
    private String about;

    @Column(name = "staff_number", nullable=false)
    private String staffNumber; 

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Social> socials = new HashSet<Social>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Expertise> expertises = new HashSet<Expertise>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Announcement> announcements = new HashSet<Announcement>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Contact contact;
}