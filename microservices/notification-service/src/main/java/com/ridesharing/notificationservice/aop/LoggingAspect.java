package com.ridesharing.notificationservice.aop;

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

    @Before("execution(* com.ridesharing.notificationservice.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Executing notification service method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.ridesharing.notificationservice.service.*.*(..))")
    public void logAfterServiceMethods(JoinPoint joinPoint) {
        logger.info("Completed notification service method: " + joinPoint.getSignature().getName());
    }
}