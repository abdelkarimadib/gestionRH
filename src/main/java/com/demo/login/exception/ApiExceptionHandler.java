package com.demo.login.exception;

import com.demo.login.dto.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestError> handleEntityIllegalArgumentException(java.lang.IllegalArgumentException exception) {
        RestError exceptionError=new RestError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestError> handleEntityConstraintViolationException(ConstraintViolationException exception) {
        RestError exceptionError=new RestError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> apiExceptionHandler(NotFoundException ex){
        RestError exceptionError=new RestError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                ex,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AlreadyExistException.class})
    public ResponseEntity<Object> apiExceptionHandler(AlreadyExistException ex){
        RestError exceptionError=new RestError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                ex,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity<Object> apiExceptionHandler(InvalidJwtAuthenticationException ex){
        RestError exceptionError=new RestError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                ex,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {WrongPasswordException.class})
    public ResponseEntity<Object> apiExceptionHandler(WrongPasswordException ex){
        RestError exceptionError=new RestError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                ex,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
