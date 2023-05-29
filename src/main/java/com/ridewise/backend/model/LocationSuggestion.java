package com.ridewise.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationSuggestion {
    private String displayName;
    private String lat;
    private String lon;
}
