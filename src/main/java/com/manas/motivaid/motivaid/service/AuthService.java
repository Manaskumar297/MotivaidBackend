package com.manas.motivaid.motivaid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.manas.motivaid.motivaid.dto.AuthResponse;
import com.manas.motivaid.motivaid.dto.CommonResponse;
import com.manas.motivaid.motivaid.dto.LoginRequest;
import com.manas.motivaid.motivaid.dto.ResetpasswordRequest;
import com.manas.motivaid.motivaid.dto.SignupRequest;
import com.manas.motivaid.motivaid.dto.UserResponse;
import com.manas.motivaid.motivaid.enums.OtpType;
import com.manas.motivaid.motivaid.model.Role;
import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.repository.RoleRepository;
import com.manas.motivaid.motivaid.repository.UserRepository;
import com.manas.motivaid.motivaid.security.jwt.JwtService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private JwtService jwtService;
    @Autowired private OtpService otpService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse signup(SignupRequest request) {
        String email = request.getEmail_id();

        // Check if email is verified
        if (!otpService.isEmailVerified(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not verified. Please verify your OTP first.");
        }

        if (userRepository.findByEmailId(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Role role = roleRepository.findByName(request.getRole().toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

        User user = new User();
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());
        user.setEmailId(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone_number(request.getPhone_number());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setCountry_code(request.getCountry_code());
        user.setRoles(Set.of(role));

        String token = jwtService.generateToken(user.getEmailId());
        user.setToken(token);

        userRepository.save(user);

        return new AuthResponse(toUserResponse(user), token);
    }


    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailId(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }

        return new AuthResponse(toUserResponse(user), user.getToken());
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmailId(),
                user.is_blocked(),
                user.is_verified(),
                user.getCreatedDatetime(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getState(),
                user.getCity(),
                user.getCountry_code(),
                user.getPhone_number(),
                user.getAddress(),
                user.getLatitude(),
                user.getLongitude(),
                user.getRoles().iterator().next().getName(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }
    
    public CommonResponse resetPassword(ResetpasswordRequest request) {
    	
    	System.out.println(request.toString());
    	   // 1️⃣ Check if the email has been verified for RESET_PASSWORD OTP
        if (!otpService.isOtpVerified(request.getEmail(), OtpType.RESET_PASSWORD)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "Email not verified. Please verify your OTP first.");
        }
    	User user=userRepository.findByEmailId(request.getEmail())
    			.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email not exists"));
    	
    	String encodedPassword = passwordEncoder.encode(request.getPassword());
    	user.setPassword(encodedPassword);
    	userRepository.save(user);
    	return CommonResponse.success("Password updated successfully for " + request.getEmail());
    }
    
  
}
