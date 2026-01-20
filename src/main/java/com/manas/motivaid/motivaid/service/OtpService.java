package com.manas.motivaid.motivaid.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manas.motivaid.motivaid.dto.CommonResponse;
import com.manas.motivaid.motivaid.enums.OtpType;
import com.manas.motivaid.motivaid.model.EmailOtp;
import com.manas.motivaid.motivaid.repository.EmailOtpRepository;
import com.manas.motivaid.motivaid.repository.UserRepository;

@Service
public class OtpService {

    @Autowired private EmailOtpRepository emailOtpRepository;
    @Autowired private EmailService emailService;
    @Autowired private UserRepository userRepository;

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public CommonResponse createOtp(String email, OtpType otpType) {

        // Block signup OTP if user already exists
        if (otpType == OtpType.SIGNUP &&
            userRepository.findByEmailId(email).isPresent()) {
            return CommonResponse.failure("Email already registered");
        }

        EmailOtp otp = emailOtpRepository
                .findByEmailAndOtpType(email, otpType)
                .orElse(new EmailOtp());

        otp.setEmail(email);
        otp.setOtp(generateOtp());
        otp.setOtpType(otpType);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setVerified(false);

        emailOtpRepository.save(otp);

        emailService.sendOtpEmail(email, otp.getOtp());

        return CommonResponse.success("OTP sent to"+ email);
    }

    public CommonResponse verifyOtp(String email, String otp, OtpType otpType) {

        EmailOtp emailOtp = emailOtpRepository
                .findByEmailAndOtpTypeAndOtp(email, otpType, otp)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        if (emailOtp.isExpired()) {
            throw new RuntimeException("OTP expired");
        }

        emailOtp.setVerified(true);
        emailOtpRepository.save(emailOtp);

        return CommonResponse.success("Verified Sucessfully");
    }

    public boolean isEmailVerified(String email) {
        return emailOtpRepository
                .findByEmailAndOtpType(email, OtpType.SIGNUP)
                .map(EmailOtp::isVerified)
                .orElse(false);
    }
    public boolean isOtpVerified(String email, OtpType otpType) {
        EmailOtp otp = emailOtpRepository.findByEmailAndOtpType(email, otpType)
                .orElseThrow(() -> new RuntimeException("Verify otp first"));
        return otp.isVerified();
    }

}

