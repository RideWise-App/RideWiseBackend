package com.ridewise.backend.service;

import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository repository;

    public VerificationTokenService(VerificationTokenRepository repository) {
        this.repository = repository;
    }

    VerificationToken getVerificationToken(String token) {
        return tokenCheck(token);
    }

    private VerificationToken tokenCheck(String token) {
        return repository.findByToken(token).orElseThrow(() -> new RuntimeException("not found"));
    }
}
