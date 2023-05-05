package com.ridewise.backend.security;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.service.ClientService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
record AuthManager(ClientService clientService, BCryptPasswordEncoder passwordEncoder) implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Client client = clientService.findByEmail(authentication.getName());
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), client.getPassword()))
            throw new BadCredentialsException("Invalid credentials");

        return new UsernamePasswordAuthenticationToken(authentication.getName(), client.getPassword());
    }
}
