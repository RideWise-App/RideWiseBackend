package com.ridewise.backend.entity;

import com.ridewise.backend.constants.Roles;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "driver")
@Data
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "verified", nullable = false)
    private Boolean verified = false;

    private Roles role = Roles.DRIVER;
}