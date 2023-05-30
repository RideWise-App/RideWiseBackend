package com.ridewise.backend.dto;

import com.ridewise.backend.constants.ClassType;

public record ClassDto(
     Long id,
     ClassType type,
     Double rate
)
{}
