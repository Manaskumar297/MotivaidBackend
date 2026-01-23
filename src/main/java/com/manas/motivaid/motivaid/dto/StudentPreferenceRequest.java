package com.manas.motivaid.motivaid.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class StudentPreferenceRequest {
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
