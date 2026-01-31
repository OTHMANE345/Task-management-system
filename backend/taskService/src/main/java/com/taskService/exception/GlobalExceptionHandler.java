package com.taskService.exception;

import com.taskService.common.response.StandardResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleTaskNotFoundException(TaskNotFoundException ex){
        StandardResponse<Object> response = new StandardResponse<>(
                null,
                false,
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<StandardResponse<Object>> handleForbiddenException(ForbiddenException ex){
        StandardResponse<Object> response = new StandardResponse<>(
                null,
                false,
                ex.getMessage(),
                null,
                HttpStatus.FORBIDDEN
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse<Object>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                (error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        StandardResponse<Object> response = new StandardResponse<>(
                null,
                false,
                "Vallidation failed",
                errors,
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse<Object>> handleGeneralException(Exception ex){
        StandardResponse<Object> response = new StandardResponse<>(
                null,
                false,
                ex.getMessage(),
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
