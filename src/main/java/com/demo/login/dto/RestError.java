package com.demo.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestError {

    private HttpStatus httpStatus;
    private String message;
    private Throwable throwable;
    private LocalDateTime timestamp;
}
