package com.demo.login.dto;

import com.demo.login.enums.Roles;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserDTO {

    private Long id;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    @NotNull(message = "firstName required")
    private String firstName;

    @NotNull(message = "lastName required")
    private String lastName;

    @NotNull(message = "email required")
    private String email;

    @NotNull(message = "password required")
    private String password;

    @NotNull(message = "roles required")
    private Set<Roles> roles;
}
