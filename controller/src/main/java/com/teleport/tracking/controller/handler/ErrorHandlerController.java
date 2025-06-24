package com.teleport.tracking.controller.handler;

import com.teleport.tracking.controller.constant.ResponseStatusCode;
import com.teleport.tracking.controller.model.APIBaseResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIBaseResponse<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(e -> e.getPropertyPath().toString().split("\\.", 2)[1] + ": " + e.getMessage())
                .toList();

        return new APIBaseResponse<>(ResponseStatusCode.BAD_REQUEST, errors);
    }
}