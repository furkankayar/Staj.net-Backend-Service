package com.service.stajnet.model;

import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobHistories")
public class JobHistory {
    
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "companyName", nullable=false)
    private String companyName;

    @Column(name = "position", nullable=false)
    private String position;

    @Builder.Default
    @Column(name = "explanation", nullable = true)
    private String explanation = "";

    @Column(name = "isWorking", nullable=false)
    private Boolean isWorking;

    @Column(name = "startDate", nullable=false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable=true)
    private LocalDateTime endDate;
}