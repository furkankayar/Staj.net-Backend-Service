package com.service.stajnet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="contacts")
public final class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="fax", nullable = true)
    private String fax;

    @Column(name = "phone", nullable=false)
    private String phone; 

    @Column(name="website", nullable=true)
    private String website;

    @Column(name="address", nullable=false)
    private String address;

    @Column(name="email", nullable=false)
    private String email;
}