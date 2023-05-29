package com.ridewise.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDetails {
    private String display_name;
    private Address address;
}
