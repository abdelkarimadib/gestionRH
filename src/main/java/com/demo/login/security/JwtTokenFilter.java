package com.demo.login.security;

import com.demo.login.dto.RestError;
import com.demo.login.exception.InvalidJwtAuthenticationException;
import com.demo.login.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class JwtTokenFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;
    private UserServiceImpl userService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);


            }
        } catch (InvalidJwtAuthenticationException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) res;

            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.setHeader("content-type", MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.getWriter().print(mapToJson(RestError.builder()
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .build()));

            return;
        }
        filterChain.doFilter(req, res);
    }

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return objectMapper.writeValueAsString(obj);
    }
}
