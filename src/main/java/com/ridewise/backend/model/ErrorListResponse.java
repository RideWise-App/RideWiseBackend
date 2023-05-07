package com.ridewise.backend.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ErrorListResponse {
    private List<String> errors;
}
