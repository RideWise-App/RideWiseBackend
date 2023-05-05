package com.ridewise.backend.service;

import com.ridewise.backend.entity.Client;
import com.ridewise.backend.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    final ClientRepository repository;
    final BCryptPasswordEncoder passwordEncoder;

    Client findById(Long id) {
        return clientCheck(repository.findById(id));
    }

    Client findByEmail(String email) {
        return clientCheck(repository.findByEmail(email));
    }

    Client clientCheck(Optional<Client> optionalClient) {
        return optionalClient.orElseThrow(() -> new RuntimeException("not found"));
    }

    void registerClient(Client client) {
        if (isClientInDb(client.getEmail())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "This email is taken!"
        );
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        saveClient(client);
    }

    Boolean isClientInDb(String email) {
        return repository.findByEmail(email).isPresent();
    }

    void saveClient(Client client) {
        repository.save(client);
    }
}
