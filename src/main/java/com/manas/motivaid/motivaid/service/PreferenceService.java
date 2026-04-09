package com.manas.motivaid.motivaid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.motivaid.motivaid.dto.CounselorPreferenceRequest;
import com.manas.motivaid.motivaid.dto.StudentPreferenceRequest;
import com.manas.motivaid.motivaid.model.CounselorPreference;
import com.manas.motivaid.motivaid.model.StudentPreference;
import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.repository.CounselorPreferenceRepository;
import com.manas.motivaid.motivaid.repository.StudentPreferenceRepository;
import com.manas.motivaid.motivaid.repository.UserRepository;

@Service
public class PreferenceService {
	private final UserRepository userRepository;
    private final StudentPreferenceRepository studentPreferenceRepository;
    private final CounselorPreferenceRepository counselorPreferenceRepository;
    
    public PreferenceService(
    		UserRepository userRepository,
    		StudentPreferenceRepository studentPreferenceRepository,
    		CounselorPreferenceRepository counselorPreferenceRepository
    		) {
    	this.userRepository=userRepository;
    	this.studentPreferenceRepository=studentPreferenceRepository;
    	this.counselorPreferenceRepository=counselorPreferenceRepository;
    }
    
    public StudentPreferenceRequest saveStudentPreference(StudentPreferenceRequest request) {
    	User user =getAuthenticatedUser();
    	validateRole(user, "STUDENT");
    	StudentPreference preference= studentPreferenceRepository.findByUser(user)
    			.orElse(new StudentPreference());
    	 preference.setUser(user);
         preference.setGender(request.getGender());
         preference.setDob(request.getDob());
         preference.setSchoolName(request.getSchoolName());
         preference.setSchoolAddress(request.getSchoolAddress());
         preference.setSchoolCity(request.getSchoolCity());
         preference.setSchoolState(request.getSchoolState());
         preference.setCurrentGradeLevel(request.getCurrentGradeLevel());
         preference.setQualificationStatus(request.getQualificationStatus());
         preference.setHighSchoolGraduationDate(request.getHighSchoolGraduationDate());
         preference.setFirstGenStudent(request.getFirstGenStudent());
         preference.setGpa(request.getGpa());
         preference.setAcademicInterestIds(request.getAcademicInterestIds());
         preference.setTestsCompleted(request.getTestsCompleted());
         preference.setFundingPlanId(request.getFundingPlanId());
         preference.setCollegeJourneyStageIds(request.getCollegeJourneyStageIds());
         
         studentPreferenceRepository.save(preference);
    	
         return mapToStudentPreferenceRequest(preference);
    }
    
    public StudentPreferenceRequest getStudentPreference() {
    	User user =getAuthenticatedUser();
    	validateRole(user, "STUDENT");
    	StudentPreference preference = studentPreferenceRepository.findByUser(user)
    			.orElseThrow(()-> new RuntimeException("Student preference not found"));
    	
	    	return mapToStudentPreferenceRequest(preference);
    	
    }
    
    public CounselorPreferenceRequest saveCounselorPreference(CounselorPreferenceRequest request) {
    	User user= getAuthenticatedUser();
    	validateRole(user, "COUNSELOR");
    	CounselorPreference preference = counselorPreferenceRepository.findByUser(user)
    			.orElse(new CounselorPreference());
    	
    	preference.setUser(user);
    	preference.setRole_title(request.getRole_title());
    	preference.setSchool_id(request.getSchool_id());
    	preference.setServices_id(request.getServices_id());
    	preference.setCommunication_prefer_id(request.getCommunication_prefer_id());
    	counselorPreferenceRepository.save(preference);
    	
    	return mapToCounselorPreferenceRequest(preference);
    	
    }
    public CounselorPreferenceRequest getCounselorPreference() {
    	User user=getAuthenticatedUser();
    	validateRole(user, "COUNSELOR");
    	CounselorPreference preference=counselorPreferenceRepository.findByUser(user)
    			.orElseThrow(()-> new RuntimeException("Counselor preference not found"));
    	return mapToCounselorPreferenceRequest(preference);
    }
    
    private User getAuthenticatedUser() {
    	String email= SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	return userRepository.findByEmailId(email)
    			.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found"));
    
    	
    }
    
    private void validateRole(User user, String rolename) {
    	boolean hasRole= user.getRoles().stream().anyMatch(role-> role.getName().equals(rolename));
    	if(!hasRole) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Access denied for role: " + rolename);
    	}
    }
    
    private StudentPreferenceRequest mapToStudentPreferenceRequest(
            StudentPreference preference
    ) {
        StudentPreferenceRequest dto = new StudentPreferenceRequest();

        dto.setGender(preference.getGender());
        dto.setDob(preference.getDob());
        dto.setSchoolName(preference.getSchoolName());
        dto.setSchoolAddress(preference.getSchoolAddress());
        dto.setSchoolCity(preference.getSchoolCity());
        dto.setSchoolState(preference.getSchoolState());
        dto.setCurrentGradeLevel(preference.getCurrentGradeLevel());
        dto.setQualificationStatus(preference.getQualificationStatus());
        dto.setHighSchoolGraduationDate(preference.getHighSchoolGraduationDate());
        dto.setFirstGenStudent(preference.getFirstGenStudent());
        dto.setGpa(preference.getGpa());
        dto.setAcademicInterestIds(preference.getAcademicInterestIds());
        dto.setTestsCompleted(preference.getTestsCompleted());
        dto.setFundingPlanId(preference.getFundingPlanId());
        dto.setCollegeJourneyStageIds(preference.getCollegeJourneyStageIds());
        
        return dto;
    }
    private CounselorPreferenceRequest mapToCounselorPreferenceRequest(CounselorPreference preference) {
    	CounselorPreferenceRequest dto=new CounselorPreferenceRequest();
    	dto.setRole_title(preference.getRole_title());
    	dto.setSchool_id(preference.getSchool_id());
    	dto.setServices_id(preference.getServices_id());
    	dto.setCommunication_prefer_id(preference.getCommunication_prefer_id());
    	return dto;
    	
    }

}
