package com.ridesharing.userservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.ridesharing.userservice.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Executing method: " + joinPoint.getSignature().getName() +
                   " in class: " + joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("execution(* com.ridesharing.userservice.service.*.*(..))")
    public void logAfterServiceMethods(JoinPoint joinPoint) {
        logger.info("Completed method: " + joinPoint.getSignature().getName() +
                   " in class: " + joinPoint.getTarget().getClass().getSimpleName());
    }
}