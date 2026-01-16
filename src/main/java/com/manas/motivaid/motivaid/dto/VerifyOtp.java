package com.manas.motivaid.motivaid.dto;

import lombok.Data;

@Data
public class VerifyOtp {
	private String email;
	private String otp;
}
