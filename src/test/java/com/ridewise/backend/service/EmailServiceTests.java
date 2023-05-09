package com.ridewise.backend.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTests {

    @Mock
    JavaMailSender sender;

    @InjectMocks
    EmailService emailService;

    @Before
    public void setup() {
        when(sender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
    }

    @Test
    public void sendVerificationEmailTest() throws MessagingException {
        emailService.sendVerificationEmail("email@example.com", UUID.randomUUID().toString());

        verify(sender).send(any(MimeMessage.class));
    }
}
