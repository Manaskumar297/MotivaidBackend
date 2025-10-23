package com.manas.motivaid.motivaid.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Table(name = "usersTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;
    private String last_name;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(nullable = false)
    private String password;

    private String address;
    private double latitude;
    private double longitude;
    private String country_code;
    private String phone_number;
    private String state;
    private String city;
    @Column(nullable = false)
    private boolean is_blocked = false;

    @Column(nullable = false)
    private boolean is_verified = false;

    @CreationTimestamp
    @Column(updatable = false, name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(length = 1000)
    private String token; // Added to store generated token

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "userRolesTable",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;
}
