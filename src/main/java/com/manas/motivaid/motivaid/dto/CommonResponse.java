package com.manas.motivaid.motivaid.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
	private String error;
	private String message;
	
	public static CommonResponse success(String message){
		return new CommonResponse(null,message);
	}
	public static CommonResponse failure(String error) {
		return new CommonResponse(error,null);
	}
	public static CommonResponse errorAndmessage(String error,String message) {
		return new CommonResponse(error,message);
	}
}
