package com.ridewise.backend.service;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.repository.VerificationTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTests {

    @Mock
    VerificationTokenRepository repository;

    @InjectMocks
    VerificationTokenService service;

    @Test
    public void tokenCheckTest() {
        VerificationToken verificationToken =
                new VerificationToken(1L, "random-uuid-string-test", new Client());

        when(repository.findByToken("random-uuid-string-test")).thenReturn(Optional.of(verificationToken));

        assertEquals(verificationToken, service.tokenCheck("random-uuid-string-test"));
        assertThrows(RuntimeException.class, () -> service.tokenCheck("non-existent-token"));
    }
}
