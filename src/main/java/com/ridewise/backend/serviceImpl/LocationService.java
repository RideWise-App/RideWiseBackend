package com.ridewise.backend.serviceImpl;

import com.ridewise.backend.entity.Location;
import com.ridewise.backend.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    LocationRepository locationRepository;

    LocationService(LocationRepository theLocationRepository) {
        locationRepository = theLocationRepository;
    }

    Location save(Location location) {
        return locationRepository.save(location);
    }
}
