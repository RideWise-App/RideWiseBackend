package com.ridewise.backend.controllerAdvice;

import com.ridewise.backend.exception.InternalServerErrorException;
import com.ridewise.backend.exception.RecordNotFoundException;
import com.ridewise.backend.model.ErrorListResponse;
import com.ridewise.backend.model.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<Object> handleRecordNotFoundException(Exception ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message));
    }

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerErrorException(Exception ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(message));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<String> errors = new ArrayList<>();

        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationEx = (ConstraintViolationException) ex.getCause();
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorListResponse(errors));
        }
    }
}