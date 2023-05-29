package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.repository.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private DriverRepository driverRepository;

    DriverService(DriverRepository theDriverRepository) {
        driverRepository = theDriverRepository;
    }
}
