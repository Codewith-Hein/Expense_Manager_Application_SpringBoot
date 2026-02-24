package com.talent.expense_manager.expense_manager.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.talent.expense_manager.expense_manager..controller..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("API Started: {}",
                joinPoint.getSignature().toShortString());
    }

    // Run after method successful
    @AfterReturning("execution(*  com.talent.expense_manager.expense_manager..controller..*(..))")
    public void logAfter(JoinPoint joinPoint) {

        log.info("API Completed: {}",
                joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(value =
            "execution(* com.talent.expense_manager..controller..*(..))",
            throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) {

        log.error("API Error in {} : {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage());
    }

}
