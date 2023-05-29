package com.ridewise.backend.controller;

import com.ridewise.backend.model.LocationSuggestion;
import com.ridewise.backend.service.MapService;
import lombok.NonNull;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("map")
public class MapController {
    private MapService mapService;

    MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/suggestion")
    public List<LocationSuggestion> getLocationSuggestions(@RequestParam(value = "search") String search, @RequestParam(value = "limit", defaultValue = "10") String limit) {
        if(search.isEmpty()) {
            throw new IllegalArgumentException("Search parameter cannot be empty");
        }
        return mapService.getLocationSuggestions(search, limit);
    }
}
