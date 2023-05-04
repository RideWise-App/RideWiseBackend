package com.ridewise.backend.service;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    final ClientRepository repository;

    Client findById(Long id) {
        return clientCheck(repository.findById(id));
    }

    Client findByEmail(String email) {
        return clientCheck(repository.findByEmail(email));
    }

    Client clientCheck(Optional<Client> optionalClient) {
        return optionalClient.orElseThrow(() -> new RuntimeException("not found"));
    }
}
