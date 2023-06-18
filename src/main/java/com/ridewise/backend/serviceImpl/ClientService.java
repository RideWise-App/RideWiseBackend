package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.dto.ClientDto;
import com.ridewise.backend.dto.ClientRegisterDto;
import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.mapper.ClientMapper;
import com.ridewise.backend.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    final ClientRepository repository;
    final BCryptPasswordEncoder passwordEncoder;
    final VerificationTokenService tokenService;
    final EmailService emailService;

    public Client findById(Long id) {
        return clientCheck(repository.findById(id));
    }

    public Client findByEmail(String email) {
        return clientCheck(repository.findByEmail(email));
    }

    public ClientDto getDtoByEmail(String email) {
        return ClientMapper.INSTANCE.mapToDto(findByEmail(email));
    }

    public Client clientCheck(Optional<Client> optionalClient) {
        return optionalClient.orElseThrow(() -> new RuntimeException("not found"));
    }

    public void registerClient(ClientRegisterDto clientData) throws MessagingException {
        if (isClientInDb(clientData.email())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "This email is taken!"
        );
        Client client = mapRegisterDto(clientData);
        client.setPassword(passwordEncoder.encode(clientData.password()));
        saveClient(client);
        VerificationToken token = tokenService.generateVerificationToken(client);
        emailService.sendVerificationEmail(clientData.email(), token.getToken());
    }

    public Boolean isClientInDb(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public void saveClient(Client client) {
        repository.save(client);
    }

    Client mapRegisterDto(ClientRegisterDto clientRegisterDto) {
        return ClientMapper.INSTANCE.registerDtoToEntity(clientRegisterDto);
    }

    public void confirmEmail(VerificationToken verificationToken) {
        Client client = verificationToken.getClient();
        client.setVerified(true);
        saveClient(client);
    }
}
