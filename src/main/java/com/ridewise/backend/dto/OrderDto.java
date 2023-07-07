package com.ridewise.backend.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record OrderDto(
        Long id,
        UserDto userDto,
        @Nullable Long driverId,
        LocationDto startLocation,
        LocationDto endLocation
) {
}
