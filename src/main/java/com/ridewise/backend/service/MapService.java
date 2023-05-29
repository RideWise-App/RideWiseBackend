package com.ridewise.backend.service;

import com.ridewise.backend.model.LocationSuggestion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MapService {
    List<LocationSuggestion> getLocationSuggestions(String search, String limit);
}
