package com.manas.motivaid.motivaid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	private UserResponse data;
	  private String token;
}
