package com.ridewise.backend.service;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.repository.VerificationTokenRepository;
import com.ridewise.backend.serviceImpl.VerificationTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTests {

    @Mock
    VerificationTokenRepository repository;

    @InjectMocks
    VerificationTokenService service;


    @Test
    public void notNullTest() {
        assertNotNull(repository);
        assertNotNull(service);
    }
}
