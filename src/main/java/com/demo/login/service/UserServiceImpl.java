package com.demo.login.service;

import com.demo.login.dto.LoginHandle;
import com.demo.login.dto.UserDTO;
import com.demo.login.entity.User;
import com.demo.login.exception.AlreadyExistException;
import com.demo.login.exception.NotFoundException;
import com.demo.login.exception.WrongPasswordException;
import com.demo.login.mapper.UserMapper;
import com.demo.login.repository.UserRepository;
import com.demo.login.security.JwtTokenProvider;
import com.demo.login.utils.log.LogArgumentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper,
                           PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider,AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
    }


    @LogArgumentResult
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("utilisateur introuvable avec id : "+id));
        return userMapper.mapToDto(user);
    }

    @LogArgumentResult
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("utilisateur introuvable avec email : "+email));
        return userMapper.mapToDto(user);
    }

    @LogArgumentResult
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            throw new AlreadyExistException("email exist deja");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userMapper.mapToDto(userRepository.save(userMapper.mapToEntity(userDTO)));
    }

    @LogArgumentResult
    public LoginHandle authenticate(LoginHandle loginHandle) {

        String username = loginHandle.getUsername();
        User userDb = userRepository.findByEmail(username).orElseThrow(
                () -> new NotFoundException("utilisateur introuvable avec email : "+username)
        );

        if (!passwordEncoder.matches(loginHandle.getPassword(), userDb.getPassword())) {
            throw new WrongPasswordException("Mot de passe invalide");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginHandle.getPassword()));

        loginHandle = new LoginHandle();
        loginHandle.setToken(jwtTokenProvider.createToken(username, findByEmail(username).getRoles()));

        return loginHandle;
    }
}
