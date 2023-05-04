package com.ridewise.backend.service;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTests {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    Client mockClient;

    @Before
    public void setup() {
        mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setEmail("email@example.com");
        mockClient.setFirstName("Name");
        mockClient.setLastName("Surname");
        mockClient.setVerified(false);
        mockClient.setPassword("password");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
    }

    @Test
    public void notNull() {
        assertNotNull(clientRepository);
        assertNotNull(clientService);
        assertNotNull(mockClient);
    }

    @Test
    public void clientCheckTest() {
        Client client = clientService.clientCheck(clientRepository.findById(1L));

        assertNotNull(client);
        assertEquals(client, mockClient);
        assertThrows(RuntimeException.class, () -> clientService.clientCheck(Optional.empty()));
    }

    @Test
    public void findByIdTest() {
        Client client = clientService.findById(1L);

        assertNotNull(client);
        assertEquals(client, mockClient);
    }

    @Test
    public void findByEmailTest() {
        when(clientRepository.findByEmail("email@example.com")).thenReturn(Optional.of(mockClient));

        Client client = clientService.findByEmail("email@example.com");

        assertNotNull(client);
        assertEquals(client, mockClient);
    }
}
