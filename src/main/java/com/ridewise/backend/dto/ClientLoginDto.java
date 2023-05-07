package com.ridewise.backend.dto;

import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class ClientLoginDto {
    @Email
    private final String email;
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(min = 8)
    private final String password;
}
