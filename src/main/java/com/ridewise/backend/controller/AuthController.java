package com.ridewise.backend.controller;

import com.ridewise.backend.dto.ClientLoginDto;
import com.ridewise.backend.dto.ClientRegisterDto;
import com.ridewise.backend.serviceImpl.ClientService;
import com.ridewise.backend.serviceImpl.VerificationTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/auth")
record AuthController(ClientService clientService, VerificationTokenService tokenService) {

    @PostMapping("/register")
    ResponseEntity<HttpStatus> registerUser(@RequestBody ClientRegisterDto client) throws MessagingException {
        clientService.registerClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Endpoint to login", summary = "User Login")
    @PostMapping("/login")
    public void login(@Parameter(name = "Email and password", required = true) ClientLoginDto clientDto){}

    @GetMapping("/{token}")
    ResponseEntity<?> verifyEmail(@PathVariable String token) {
        clientService.confirmEmail(tokenService.getVerificationToken(token));
        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
    }

    @Operation(description = "Endpoint to logout", summary = "Logout")
    @GetMapping ("/logout")
    public void logout(){}
}
