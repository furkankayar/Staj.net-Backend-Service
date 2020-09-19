package com.service.stajnet.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "educationHistories")
public class EducationHistory {
    
    public enum Type{
        PRIMARY {
            @Override
            public String toString(){
                return "Primary";
            }
        },
        HIGH_SCHOOL {
            @Override
            public String toString(){
                return "High School";
            }
        },
        ASSOCIATE_DEGREE {
            @Override
            public String toString(){
                return "Associate Degree";
            }
        },
        UNDERGRADUATE {
            @Override
            public String toString(){
                return "Undergraduate";
            }
        },
        GRADUATE {
            @Override
            public String toString(){
                return "Graduate";
            }
        },
        POSTGRADUATE {
            @Override
            public String toString() {
                return "Postgraduate";
            }
        }
    }

    public enum Status {
        STUDENT {
            @Override
            public String toString() {
                return "Student";
            }
        },
        GRADUATED {
            @Override
            public String toString() {
                return "Graduated";
            }
        }
    }

    public enum GradingSystem {
        FOUR,
        HUNDRED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schoolName", nullable=false)
    private String schoolName;

    @Column(name = "facultyName", nullable=true)
    private String facultyName;

    @Column(name = "departmentName", nullable=true)
    private String departmentName;

    @Column(name = "type", nullable=false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "status", nullable=false)
    @Enumerated(EnumType.STRING)
    private Status educationStatus;

    @Column(name = "classNumber", nullable=true)
    private String classNumber;

    @Column(name = "startDate", nullable=false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable=true)
    private LocalDateTime endDate;

    @Column(name = "gradingSystem", nullable=false)
    @Enumerated(EnumType.STRING)
    private GradingSystem gradingSystem;

    @Column(name = "gpa", nullable=false)
    private float gpa;

    public String getEducationStatus(){
        return this.educationStatus.toString();
    }

    public String getType(){
        return this.type.toString();
    }
}