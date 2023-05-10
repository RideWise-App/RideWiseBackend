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
    public void tokenCheckTest() {
        VerificationToken verificationToken =
                new VerificationToken(1L, "random-uuid-string-test", new Client());

        when(repository.findByToken("random-uuid-string-test")).thenReturn(Optional.of(verificationToken));

        assertEquals(verificationToken, service.tokenCheck("random-uuid-string-test"));
        assertThrows(RuntimeException.class, () -> service.tokenCheck("non-existent-token"));
    }

    @Test
    public void mapTokenTest() {
        String token = "random-uuid-string-test";
        Client client = new Client();
        VerificationToken verificationToken = service.mapToken(token, client);

        assertEquals(token, verificationToken.getToken());
        assertEquals(client, verificationToken.getClient());
    }

    @Test
    public void generateVerificationTokenTest() {
        Client client = new Client();

        service.generateVerificationToken(client);

        verify(repository).save(any(VerificationToken.class));
    }

    @Test
    public void getVerificationTokenTest() {
        VerificationToken verificationToken =
                new VerificationToken(1L, "random-uuid-string-test", new Client());

        when(repository.findByToken("random-uuid-string-test")).thenReturn(Optional.of(verificationToken));

        assertEquals(verificationToken, service.getVerificationToken("random-uuid-string-test"));
    }
}
