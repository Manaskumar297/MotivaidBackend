package com.manas.motivaid.motivaid.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.manas.motivaid.motivaid.dto.AuthResponse;
import com.manas.motivaid.motivaid.dto.CommonResponse;
import com.manas.motivaid.motivaid.dto.LoginRequest;
import com.manas.motivaid.motivaid.dto.ResetpasswordRequest;
import com.manas.motivaid.motivaid.dto.SendVerificationOtp;
import com.manas.motivaid.motivaid.dto.SignupRequest;
import com.manas.motivaid.motivaid.dto.VerifyOtp;
import com.manas.motivaid.motivaid.enums.OtpType;
import com.manas.motivaid.motivaid.service.AuthService;
import com.manas.motivaid.motivaid.service.OtpService;
import com.manas.motivaid.motivaid.enums.*;
import ch.qos.logback.core.joran.conditional.IfAction;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private OtpService otpService;
    
    
    @PostMapping("/send-otp")
    public CommonResponse requestOtp(@RequestBody SendVerificationOtp request) {
         return	otpService.createOtp(request.getEmail(),OtpType.SIGNUP);
    	  
    }
    
    @PostMapping("/verify-otp")
    public CommonResponse verifyOtp(@RequestBody VerifyOtp request) {

     return  otpService.verifyOtp(request.getEmail(),request.getOtp(),OtpType.SIGNUP);   
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    
    
    @PostMapping("/send-forgot-password-otp")
    public CommonResponse requestForgotpasswordOtp(@RequestBody SendVerificationOtp request) {
    	return otpService.createOtp(request.getEmail(),OtpType.RESET_PASSWORD);
    }
    
    @PostMapping("/verify-forgot-password-otp")
    public CommonResponse verifyForgotpasswordOtp(@RequestBody VerifyOtp request) {
    	return otpService.verifyOtp(request.getEmail(), request.getOtp(), OtpType.RESET_PASSWORD);
    }
    
    @PutMapping("/reset-password")
    public CommonResponse resetPassword(@RequestBody ResetpasswordRequest request) {
    	System.out.println(request.toString());
    	return authService.resetPassword(request);
    }
    
  
   
}
