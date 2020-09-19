package com.service.stajnet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder.Default
    @Column(name = "country")
    private String country = "-";

    @Builder.Default
    @Column(name = "city")
    private String city = "-";

    @Builder.Default
    @Column(name = "district")
    private String district = "-";

    @Builder.Default
    @Column(name = "address")
    private String address = "-";

    @Builder.Default
    @Column(name = "postCode")
    private String postCode = "-";
}