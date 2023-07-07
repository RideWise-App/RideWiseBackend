package com.ridewise.backend.dto;

import java.math.BigDecimal;

public record LocationDto(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
