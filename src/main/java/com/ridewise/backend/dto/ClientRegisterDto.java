package com.ridewise.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public record ClientRegisterDto(@NotBlank @NotEmpty @NotNull @Length(max = 50) String firstName,
                                @NotBlank @NotEmpty @NotNull @Length(max = 50) String lastName, @Email String email,
                                @NotBlank @NotEmpty @NotNull @Length(min = 8) String password) {}
