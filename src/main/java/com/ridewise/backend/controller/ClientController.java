package com.ridewise.backend.controller;

import com.ridewise.backend.dto.UserDto;
import com.ridewise.backend.serviceImpl.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
record ClientController(ClientService clientService) {

    @GetMapping
    ResponseEntity<UserDto> fetchAuthenticatedUser(@AuthenticationPrincipal String email) {
        return new ResponseEntity<>(clientService.getDtoByEmail(email), HttpStatus.OK);
    }
}
