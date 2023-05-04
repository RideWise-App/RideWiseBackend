package com.ridewise.backend.service;

import com.ridewise.backend.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {

    final ClientRepository repository;
}
