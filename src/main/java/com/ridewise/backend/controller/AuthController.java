package com.ridewise.backend.controller;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
record AuthController(ClientService clientService) {

    @PostMapping("/register")
    ResponseEntity<HttpStatus> registerUser(@RequestBody Client client) {
        clientService.registerClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
