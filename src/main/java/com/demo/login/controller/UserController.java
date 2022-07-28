package com.demo.login.controller;

import com.demo.login.dto.LoginHandle;
import com.demo.login.dto.UserDTO;
import com.demo.login.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<LoginHandle> authenticate(
            @Valid @RequestBody LoginHandle loginHandle) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.authenticate(loginHandle));
    }
}
