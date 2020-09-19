package com.service.stajnet.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="contacts")
public final class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name="fax", nullable = true)
    private String fax = "-";

    @Builder.Default
    @Column(name = "phone", nullable=false)
    private String phone = "-";

    @Builder.Default
    @Column(name="website", nullable=true)
    private String website = "-";

    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address = Address.builder().build();

}