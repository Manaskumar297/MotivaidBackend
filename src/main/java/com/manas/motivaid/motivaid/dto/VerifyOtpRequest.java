package com.manas.motivaid.motivaid.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
	private String email;
	private String otp;
}
