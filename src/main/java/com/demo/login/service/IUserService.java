package com.demo.login.service;

import com.demo.login.dto.LoginHandle;
import com.demo.login.dto.UserDTO;

public interface IUserService {

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    UserDTO createUser(UserDTO userDTO);

    LoginHandle authenticate(LoginHandle loginHandle);
}
