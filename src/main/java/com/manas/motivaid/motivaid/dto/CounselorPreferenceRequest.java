package com.manas.motivaid.motivaid.dto;

import lombok.Data;

@Data
public class CounselorPreferenceRequest {
	private  String role_title;
	private  int school_id;
	private  String services_id;
	private  String communication_prefer_id;

}
