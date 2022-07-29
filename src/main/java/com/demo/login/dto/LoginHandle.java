package com.demo.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class LoginHandle {

    @NotNull(message = "userName required")
    private String username;

    @NotNull(message = "password required")
    private String password;

    private Long id;

    private String token;
}
