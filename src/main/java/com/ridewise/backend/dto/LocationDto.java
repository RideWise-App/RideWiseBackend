package com.ridewise.backend.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LocationDto(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
