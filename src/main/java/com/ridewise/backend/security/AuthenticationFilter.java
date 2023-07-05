package com.ridewise.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridewise.backend.dto.UserDto;
import com.ridewise.backend.dto.UserLoginDto;
import com.ridewise.backend.serviceImpl.ClientService;
import com.ridewise.backend.serviceImpl.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    final AuthenticationManager authenticationManager;
    final ClientService clientService;
    final DriverService driverService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserLoginDto user = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.email(), user.password(),
                    List.of(new SimpleGrantedAuthority(user.role().name())));
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        String[] roles = authResult.getAuthorities().stream().map(String::valueOf).toArray(String[]::new);
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withArrayClaim("Roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + 7200000))
                .sign(Algorithm.HMAC512(SecurityConfig.secretKey));

        UserDto userDto = Objects.equals(roles[0], "USER") ?
                clientService.getDtoByEmail(authResult.getName()) : driverService.getDtoByEmail(authResult.getName());
        response.addCookie(generateBearerCookie(token));
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new ObjectMapper().writeValueAsString(userDto));
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    private Cookie generateBearerCookie(String token) {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400);
//        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
