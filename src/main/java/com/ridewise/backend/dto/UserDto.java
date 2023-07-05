package com.ridewise.backend.dto;

import com.ridewise.backend.constants.Roles;

import javax.validation.constraints.NotNull;

public record UserDto(@NotNull Long id, @NotNull String firstName,
                      @NotNull String lastName, @NotNull String email,
                      @NotNull Roles role) {
}
