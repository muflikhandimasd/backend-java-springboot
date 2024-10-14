package com.muflikhan.backend.controllers;

import com.muflikhan.mggmuflikhan.dtos.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController  {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("msg", ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                null,
                ex.getReason(),
                errorDetails
        );

        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error)->{
            errors.put(((FieldError)error).getField(), error.getDefaultMessage());
        });


            ApiResponse<Object> response = new ApiResponse<>(
                "error",
                null,
                "Validation failed",
                errors
        );



        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFoundException(EntityNotFoundException e){
        Map<String, String> errors = new HashMap<>();

        errors.put("details", e.getMessage());


        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                null,
                "Data Not Found",
                errors
        );



        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}
