package com.staimov.employee_directory.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * com.staimov.employee_directory.controller.*.*(jakarta.servlet.http.HttpServletRequest,..))" +
            " && !execution(* com.staimov.employee_directory.controller.CustomErrorController.*(..))")
    private void forRequestMethods() {}

    @Before("forRequestMethods() && args(request,..)")
    public void before(JoinPoint joinPoint, HttpServletRequest request) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("{}, {}, {}", methodName, request.getMethod(), request.getRequestURL());
    }
}
