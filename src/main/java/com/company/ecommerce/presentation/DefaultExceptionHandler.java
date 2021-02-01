package com.company.ecommerce.presentation;

import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class DefaultExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<GenericResponse<String>> handleException(IllegalArgumentException e) {
        GenericResponse<String> genericResponse = new GenericResponse<>(e.getMessage());
        return new ResponseEntity<>(genericResponse, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericResponse<String>> handleException(Exception e) {
        GenericResponse<String> genericResponse = new GenericResponse<>(e.getMessage());
        return new ResponseEntity<>(genericResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<GenericResponse<String>> handleException(ResourceNotFoundException e) {
        GenericResponse<String> genericResponse = new GenericResponse<>(e.getMessage());
        return new ResponseEntity<>(genericResponse, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<GenericResponse<String>> handleException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        GenericResponse<String> genericResponse = new GenericResponse<>("Error parsing payload");
        if (cause instanceof JsonMappingException) {
            JsonMappingException jsonExcp = (JsonMappingException) cause;
            genericResponse.setMessage(jsonExcp.getOriginalMessage());
        }
        return new ResponseEntity<>(genericResponse, null, HttpStatus.BAD_REQUEST);
    }
}
