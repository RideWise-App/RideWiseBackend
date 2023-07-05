package com.ridewise.backend.security;

import com.ridewise.backend.constants.Roles;
import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.Driver;
import com.ridewise.backend.serviceImpl.ClientService;
import com.ridewise.backend.serviceImpl.DriverService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
record AuthManager(ClientService clientService,
                   DriverService driverService,
                   BCryptPasswordEncoder passwordEncoder) implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String role = authentication.getAuthorities().stream().findFirst().get().toString();
        if (role.equals("USER")) {
            Client client = clientService.findByEmail(authentication.getName());
            if (!client.getVerified()) throw new RuntimeException("Please confirm Your email first");
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), client.getPassword()))
                throw new BadCredentialsException("Invalid credentials");
            return new UsernamePasswordAuthenticationToken(authentication.getName(), client.getPassword(),
                    List.of(new SimpleGrantedAuthority(role)));
        } else if (role.equals("DRIVER")) {
            Driver driver = driverService.findByEmail(authentication.getName());
            if (!driver.getVerified()) throw new RuntimeException("Please confirm Your email first");
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), driver.getPassword()))
                throw new BadCredentialsException("Invalid credentials");
            return new UsernamePasswordAuthenticationToken(authentication.getName(), driver.getPassword(),
                    List.of(new SimpleGrantedAuthority(role)));
        }
        throw new RuntimeException("error");
    }
}
