package com.service.stajnet.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "announcements")
public final class Announcement {
    
    public enum Type{
        INTERNSHIP,
        PARTTIME,
        FULLTIME
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deadline", nullable=false)
    private Timestamp deadline;

    @Column(name = "start_date", nullable=false)
    private Timestamp start_date;

    @Column(name = "title", nullable=false)
    private String title;

    @Column(name = "type", nullable=false)
    private Type type;

    @Column(name = "update_date", nullable=false)
    private Timestamp update_date;

    @Column(name = "definition", nullable=false)
    private String definition;

}