package com.manas.motivaid.motivaid.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Fallback;

import com.manas.motivaid.motivaid.enums.OtpType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "email_otp",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "otpType"})
    }
)
public class EmailOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType otpType;

    private LocalDateTime createdAt;

    private boolean verified;

    public boolean isExpired() {
        return createdAt.plusMinutes(10).isBefore(LocalDateTime.now());
    }
}

