package com.manas.motivaid.motivaid.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EmailOtp {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long id;
private String email;
private String otp;
private LocalDateTime createdAt;
private boolean verified;
public boolean isExpired() {
    return createdAt.plusMinutes(10).isBefore(LocalDateTime.now());
}

}
