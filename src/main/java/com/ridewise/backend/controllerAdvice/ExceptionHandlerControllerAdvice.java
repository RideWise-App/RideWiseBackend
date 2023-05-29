package com.ridewise.backend.controllerAdvice;

import com.ridewise.backend.exception.InternalServerErrorException;
import com.ridewise.backend.exception.RecordNotFoundException;
import com.ridewise.backend.model.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRecordNotFoundException(Exception ex) {
        ExceptionMessageDto exceptionMessage = new ExceptionMessageDto(List.of(ex.getMessage()));
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerErrorException(Exception ex) {
        ExceptionMessageDto exceptionMessage = new ExceptionMessageDto(List.of(ex.getMessage()));
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<String> errors = new ArrayList<>();

        if (ex.getCause() instanceof ConstraintViolationException constraintViolationEx) {
            if (constraintViolationEx.getConstraintName().endsWith("_unq")) {
                errors.add("Unique constraint violation: " + ex.getMessage());
            } else if (constraintViolationEx.getConstraintName().contains("_fk_")) {
                errors.add("Foreign key constraint violation: " + ex.getMessage());
            } else if (constraintViolationEx.getConstraintName().endsWith("_pk")) {
                errors.add("Primary key constraint violation: " + ex.getMessage());
            } else if (constraintViolationEx.getConstraintName().endsWith("_ck")) {
                errors.add("Check constraint violation: " + ex.getMessage());
            }
        }

        if (errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An unexpected error occurred"));
        } else {
            return new ResponseEntity<>(new ExceptionMessageDto(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
        return new ResponseEntity<>(new ExceptionMessageDto(errors), HttpStatus.BAD_REQUEST);
    }
}