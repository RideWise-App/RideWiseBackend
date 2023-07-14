package com.ridewise.backend.controller;

import com.ridewise.backend.dto.LocationDto;
import com.ridewise.backend.dto.OrderDto;
import com.ridewise.backend.serviceImpl.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("")
    ResponseEntity<List<OrderDto>> findOrders() {
        List<OrderDto> orders = service.fetchOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/{id}")
    ResponseEntity<HttpStatus> acceptOrder(@PathVariable Long id, Authentication authentication) {
        service.acceptOrder(id, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
