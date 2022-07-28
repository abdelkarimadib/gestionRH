package com.demo.login.security;

import com.demo.login.exception.NotFoundException;
import com.demo.login.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository users) {
        this.userRepository = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        return userRepository.findByEmail(username).orElseThrow(()
                -> new NotFoundException("username :"+username));
    }
}