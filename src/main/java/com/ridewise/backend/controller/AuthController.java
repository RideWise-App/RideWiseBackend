package com.ridewise.backend.controller;

import com.ridewise.backend.dto.ClientRegisterDto;
import com.ridewise.backend.service.ClientService;
import com.ridewise.backend.service.VerificationTokenService;
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

    @PostMapping
    ResponseEntity<?> verifyEmail(@RequestParam String token) {
        clientService.confirmEmail(tokenService.getVerificationToken(token));
        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
    }
}
