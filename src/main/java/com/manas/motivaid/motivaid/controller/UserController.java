package com.manas.motivaid.motivaid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manas.motivaid.motivaid.dto.ChangePasswordRequest;
import com.manas.motivaid.motivaid.dto.CommonResponse;
import com.manas.motivaid.motivaid.dto.CounselorPreferenceRequest;
import com.manas.motivaid.motivaid.dto.LoginRequest;
import com.manas.motivaid.motivaid.dto.StudentPreferenceRequest;
import com.manas.motivaid.motivaid.model.CounselorPreference;
import com.manas.motivaid.motivaid.service.PreferenceService;
import com.manas.motivaid.motivaid.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PreferenceService preferenceService;
	
	@DeleteMapping("/delete-user")
	public CommonResponse deleteUser(@RequestBody LoginRequest request) {
		return userService.deleteUser(request);
	}
	
	@PutMapping("/change-password")
	public CommonResponse changepassword(@RequestBody ChangePasswordRequest request) {
		return userService.changePassword(request);
	}
	
	@PutMapping("/upadte-student-profile")
	public StudentPreferenceRequest studentPreference(@RequestBody StudentPreferenceRequest request) {
		return preferenceService.saveStudentPreference(request);
	}
	@GetMapping("/get-student-profile")
	public StudentPreferenceRequest getStudentPreference() {
		return preferenceService.getStudentPreference();
	}
	
	@PutMapping("/update-counselor-profile")
	public CounselorPreferenceRequest counselorPreference(@RequestBody CounselorPreferenceRequest request) {
		return preferenceService.saveCounselorPreference(request);
	}
	@GetMapping("/get-counselor-profile")
	public CounselorPreferenceRequest getCounselorPreference() {
		return preferenceService.getCounselorPreference();
	}
	
}
