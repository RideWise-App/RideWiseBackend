package com.ridewise.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Address {
    private String houseNumber;
    private String road;
    private String neighbourhood;
    private String quarter;
    private String suburb;
    private String city;
    private String state;
    private String iso3166_2_lvl4;
    private String postcode;
    private String country;
    private String countryCode;
}
