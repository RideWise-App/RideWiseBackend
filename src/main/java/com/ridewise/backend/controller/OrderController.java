package com.ridewise.backend.controller;

import com.ridewise.backend.dto.LocationDto;
import com.ridewise.backend.serviceImpl.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
record OrderController(OrderService service) {

    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    ResponseEntity<HttpStatus> createOrder(@RequestBody Map<String, LocationDto> locations, Authentication authentication) {
        service.initializeOrder(locations, authentication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
