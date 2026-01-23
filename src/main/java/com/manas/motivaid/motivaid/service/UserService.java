package com.manas.motivaid.motivaid.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.motivaid.motivaid.dto.ChangePasswordRequest;
import com.manas.motivaid.motivaid.dto.CommonResponse;
import com.manas.motivaid.motivaid.dto.LoginRequest;
import com.manas.motivaid.motivaid.enums.OtpType;
import com.manas.motivaid.motivaid.model.EmailOtp;
import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.repository.EmailOtpRepository;
import com.manas.motivaid.motivaid.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailOtpRepository emailOtpRepository;
	@Autowired
	private OtpService otpService;
	
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	public CommonResponse deleteUser(LoginRequest request) {
		User user=userRepository.findByEmailId(request.getEmail())
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email not exist"));
		
		 if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
	        }
		 userRepository.delete(user);
		 
		return CommonResponse.success("Account deleted successfully");
		
	}
	
	public CommonResponse changePassword(ChangePasswordRequest request) {
		User user=userRepository.findByEmailId(request.getEmail())
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not exist"));
		
		if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password does not matches");
		}
		String encodedNewPasswordString=passwordEncoder.encode(request.getNewPassword());
		user.setPassword(encodedNewPasswordString);
		userRepository.save(user);
		
		otpService.deleteOtp(request.getEmail(), OtpType.RESET_PASSWORD);
		otpService.deleteOtp(request.getEmail(), OtpType.SIGNUP);
		return CommonResponse .success("Sucessfully changed password");
	}
	
	
	

}
