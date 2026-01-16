package com.manas.motivaid.motivaid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.EmailOtp;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {
	Optional<EmailOtp>findByEmailAndVerifiedFalse(String email);
	Optional<EmailOtp>findByEmailAndOtp(String email,String otp);

}
