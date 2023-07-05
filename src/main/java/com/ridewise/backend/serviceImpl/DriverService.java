package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.dto.UserRegisterDto;
import com.ridewise.backend.entity.Client;
import com.ridewise.backend.entity.Driver;
import com.ridewise.backend.entity.VerificationToken;
import com.ridewise.backend.mapper.DriverMapper;
import com.ridewise.backend.repository.DriverRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;

@Service
public class DriverService {
    final DriverRepository repository;
    final BCryptPasswordEncoder passwordEncoder;
    final VerificationTokenService tokenService;
    final EmailService emailService;

    public DriverService(DriverRepository repository, BCryptPasswordEncoder passwordEncoder,
                         VerificationTokenService tokenService, EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    public void registerDriver(UserRegisterDto driverData) throws MessagingException {
        if (isDriverInDb(driverData.email())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "This email is taken!"
        );
        Driver driver = mapRegisterDto(driverData);
        driver.setPassword(passwordEncoder.encode(driverData.password()));
        saveDriver(driver);
        VerificationToken token = tokenService.generateVerificationToken(driver.getId(), driver.getRole());
        emailService.sendVerificationEmail(driverData.email(), token.getToken());
    }

    public Boolean isDriverInDb(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public void saveDriver(Driver driver) {
        repository.save(driver);
    }

    Driver mapRegisterDto(UserRegisterDto userRegisterDto) {
        return DriverMapper.INSTANCE.registerDtoToEntity(userRegisterDto);
    }

    public void confirmEmail(VerificationToken verificationToken) {
        Driver driver = findById(verificationToken.getId());
        driver.setVerified(true);
        saveDriver(driver);
    }

    private Driver findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}