package com.manas.motivaid.motivaid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.manas.motivaid.motivaid.dto.AuthResponse;
import com.manas.motivaid.motivaid.dto.LoginRequest;
import com.manas.motivaid.motivaid.dto.SendVerificationOtp;
import com.manas.motivaid.motivaid.dto.SignupRequest;
import com.manas.motivaid.motivaid.dto.VerifyOtp;
import com.manas.motivaid.motivaid.service.AuthService;
import com.manas.motivaid.motivaid.service.OtpService;

import ch.qos.logback.core.joran.conditional.IfAction;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private OtpService otpService;
    
    
    @PostMapping("/send-otp")
    public String requestOtp(@RequestBody SendVerificationOtp request) {
         return	otpService.createOtp(request.getEmail());
    	  
    }
    
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtp request) {

        boolean val = otpService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );

        if (val) {
            return "Verified Successfully";
        } else {
            // This should normally never happen if service throws exceptions
            return "OTP verification failed";
        }
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    
   
}
