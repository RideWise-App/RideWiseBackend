package com.ridewise.backend.dto;

import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class ClientRegisterDto {
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(max = 50)
    private final String firstName;
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(max = 50)
    private final String lastName;
    @Email
    private final String email;
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(min = 8)
    private final String password;
}
