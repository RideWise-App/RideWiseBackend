package com.ridewise.backend.controller;

import com.ridewise.backend.constants.Roles;
import com.ridewise.backend.dto.UserLoginDto;
import com.ridewise.backend.dto.UserRegisterDto;
import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.serviceImpl.ClientService;
import com.ridewise.backend.serviceImpl.DriverService;
import com.ridewise.backend.serviceImpl.VerificationTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/auth")
record AuthController(ClientService clientService, DriverService driverService, VerificationTokenService tokenService) {

    @PostMapping("/register")
    ResponseEntity<HttpStatus> registerUser(@RequestBody UserRegisterDto user) throws MessagingException {
        if (user.role() == Roles.USER) clientService.registerClient(user);
        else driverService.registerDriver(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Endpoint to login", summary = "User Login")
    @PostMapping("/login")
    public void login(@Parameter(name = "Email and password and role", required = true) UserLoginDto clientDto){}

    @GetMapping("/{token}")
    ResponseEntity<?> verifyEmail(@PathVariable String token) {
        VerificationToken verificationToken = tokenService.getVerificationToken(token);
        if (verificationToken.getRole() == Roles.DRIVER) driverService.confirmEmail(verificationToken);
        else clientService.confirmEmail(verificationToken);
        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
    }

    @Operation(description = "Endpoint to logout", summary = "Logout")
    @GetMapping ("/logout")
    public void logout(){}
}
