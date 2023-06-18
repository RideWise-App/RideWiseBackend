package com.ridewise.backend.dto;

import javax.validation.constraints.NotNull;

public record ClientDto(@NotNull Long id,@NotNull String firstName) {
}
