package com.ridesharing.paymentservice.aop;

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

    @Before("execution(* com.ridesharing.paymentservice.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Executing payment service method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.ridesharing.paymentservice.service.*.*(..))")
    public void logAfterServiceMethods(JoinPoint joinPoint) {
        logger.info("Completed payment service method: " + joinPoint.getSignature().getName());
    }
}