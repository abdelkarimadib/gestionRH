package com.demo.login.utils.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("@annotation(LogArgumentResult)")
    public void traceBefore(JoinPoint joinPoint) {
        log.info("SIGNATURE : {}",joinPoint.getSignature());
        log.info("INPUT : {}",Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "@annotation(LogArgumentResult)",returning = "returnValue")
    public void traceAfter(Object returnValue) {
        log.info("OUTPUT : {}",returnValue);
    }

    @AfterThrowing(value = "@annotation(LogArgumentResult)",throwing = "exception")
    public void traceAfterException(Throwable exception) {
        log.info("EXCEPTION : {}",exception.getMessage());
    }
}
