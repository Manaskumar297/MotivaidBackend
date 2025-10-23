package com.manas.motivaid.motivaid.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String role; 
    private String first_name;
    private String last_name;
    private String email_id;
    private String password;
    private String city;
    private String address;
    private double latitude;
    private double longitude;
    private String country_code;
    private String phone_number;
    private String state;

}
