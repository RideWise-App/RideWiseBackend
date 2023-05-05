package com.ridewise.backend.service;

import com.ridewise.backend.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    LocationRepository locationRepository;

    LocationService(LocationRepository theLocationRepository) {
        locationRepository = theLocationRepository;
    }
}
