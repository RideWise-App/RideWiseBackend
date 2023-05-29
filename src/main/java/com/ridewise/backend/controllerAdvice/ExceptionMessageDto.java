package com.ridewise.backend.controllerAdvice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
class ExceptionMessageDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final List<String> messages;

    public ExceptionMessageDto(List<String> messages) {
        this.timestamp = LocalDateTime.now();
        this.messages = messages;
    }
}
