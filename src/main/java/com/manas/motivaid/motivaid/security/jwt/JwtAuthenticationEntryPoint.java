package com.manas.motivaid.motivaid.security.jwt;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manas.motivaid.motivaid.dto.ApiError;
import com.manas.motivaid.motivaid.dto.CommonResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		
	CommonResponse erroResponse= CommonResponse.errorAndmessage("Unauthorized", "Invalid or missing authentication token");
	
	 String json = objectMapper.writeValueAsString(erroResponse);

     response.getWriter().write(json);		
	}

	
}
