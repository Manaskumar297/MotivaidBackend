package com.manas.motivaid.motivaid.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.enums.OtpType;
import com.manas.motivaid.motivaid.model.EmailOtp;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {

    Optional<EmailOtp> findByEmailAndOtpType(String email, OtpType otpType);

    Optional<EmailOtp> findByEmailAndOtpTypeAndOtp(
        String email,
        OtpType otpType,
        String otp
    );
//    Optional<EmailOtp>findByEmail(String email);
    void deleteByCreatedAtBefore(LocalDateTime time);
    void deleteByEmailAndOtpType(String email, OtpType otpType);

}

