package com.ridewise.backend.service;

import com.ridewise.backend.dto.ClientRegisterDto;
import com.ridewise.backend.entity.Client;
import com.ridewise.backend.mapper.ClientMapper;
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

    public Client findByEmail(String email) {
        return clientCheck(repository.findByEmail(email));
    }

    Client clientCheck(Optional<Client> optionalClient) {
        return optionalClient.orElseThrow(() -> new RuntimeException("not found"));
    }

    public void registerClient(ClientRegisterDto clientData) {
        if (isClientInDb(clientData.email())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "This email is taken!"
        );
        Client client = mapRegisterDto(clientData);
        client.setPassword(passwordEncoder.encode(clientData.password()));
        saveClient(client);
    }

    Boolean isClientInDb(String email) {
        return repository.findByEmail(email).isPresent();
    }

    void saveClient(Client client) {
        repository.save(client);
    }

    Client mapRegisterDto(ClientRegisterDto clientRegisterDto) {
        return ClientMapper.INSTANCE.registerDtoToEntity(clientRegisterDto);
    }
}
