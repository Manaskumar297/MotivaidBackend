package com.manas.motivaid.motivaid.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "student_preferences")
public class StudentPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String gender;

    private LocalDate dob;

    private String schoolName;
    private String schoolAddress;
    private String schoolCity;
    private String schoolState;

    private String currentGradeLevel;
    private String qualificationStatus;

    private LocalDate highSchoolGraduationDate;

    private Boolean firstGenStudent;

    private Double gpa;

    private List<String> academicInterestIds;
    private String testsCompleted;
    private List<String> fundingPlanId;
    private int collegeJourneyStageIds;
}

