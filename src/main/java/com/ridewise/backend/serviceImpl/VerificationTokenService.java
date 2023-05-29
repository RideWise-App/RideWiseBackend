package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.mapper.VerificationTokenMapper;
import com.ridewise.backend.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository repository;

    public VerificationTokenService(VerificationTokenRepository repository) {
        this.repository = repository;
    }

    public VerificationToken generateVerificationToken(Client client) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = mapToken(token, client);
        return repository.save(verificationToken);
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenCheck(token);
    }

    public VerificationToken tokenCheck(String token) {
        return repository.findByToken(token).orElseThrow(() -> new RuntimeException("not found"));
    }

    public VerificationToken mapToken(String token, Client client) {
        return VerificationTokenMapper.INSTANCE.mapToken(token, client);
    }
}
