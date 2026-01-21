package com.manas.motivaid.motivaid.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manas.motivaid.motivaid.repository.EmailOtpRepository;

import jakarta.transaction.Transactional;

@Service
public class OtpCleanupService {
private  final EmailOtpRepository emailOtpRepository;

public OtpCleanupService(EmailOtpRepository emailOtpRepository) {
	this.emailOtpRepository=emailOtpRepository;
}

@Scheduled(fixedRate = 600000) // every 10 minutes
@Transactional
public void deleteExpiredOtps() {
    emailOtpRepository.deleteByCreatedAtBefore(
        LocalDateTime.now().minusMinutes(10)
    );
}

}
