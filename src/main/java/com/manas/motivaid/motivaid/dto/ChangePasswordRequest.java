package com.manas.motivaid.motivaid.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	private String email;
	private String oldPassword;
	private String newPassword;

}
