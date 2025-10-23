package com.manas.motivaid.motivaid.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long user_id;
    private String email_id;
    private boolean blocked;
    private boolean verified;
    private LocalDateTime created_datetime;
    private String first_name;
    private String last_name;
    private String state;
    private String city;
    private String country_code;
    private String phone_number;
    private String address;
    private Double latitude;
    private Double longitude;
    private String role_id;
    private Set<String> roles;
}
