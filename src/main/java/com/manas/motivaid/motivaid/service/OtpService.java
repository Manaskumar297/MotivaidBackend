package com.manas.motivaid.motivaid.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manas.motivaid.motivaid.model.EmailOtp;
import com.manas.motivaid.motivaid.repository.EmailOtpRepository;

@Service
public class OtpService {

    @Autowired
    private EmailOtpRepository emailOtpRepository;
    @Autowired
    private EmailService emailService;

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public String createOtp(String email) {

        EmailOtp otp = emailOtpRepository
                .findByEmailAndVerifiedFalse(email)
                .orElse(new EmailOtp());

        otp.setEmail(email);
        otp.setOtp(generateOtp());
        otp.setCreatedAt(LocalDateTime.now());
        otp.setVerified(false);

        emailOtpRepository.save(otp);
        // ✅ Send email
        emailService.sendOtpEmail(email, otp.getOtp());

        // TEMP: Console log (replace with email sender)
        System.out.println("OTP for " + email + " : " + otp.getOtp());

        return "otp sent to"+email;
    }

    public Boolean verifyOtp(String email, String otp) {

        EmailOtp emailOtp = emailOtpRepository
                .findByEmailAndOtp(email, otp)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        if (emailOtp.isExpired()) {
            throw new RuntimeException("OTP expired");
        }

        emailOtp.setVerified(true);
        emailOtpRepository.save(emailOtp);
       

        return true;
    }

    public boolean isEmailVerified(String email) {
        return emailOtpRepository.findByEmailAndVerifiedFalse(email).isEmpty();
    }
}
