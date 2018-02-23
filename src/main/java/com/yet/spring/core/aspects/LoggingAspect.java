package com.yet.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {



    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {}

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("BEFORE: " +
        joinPoint.getTarget().getClass().getSimpleName() + " " +
        joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allLogEventMethods()"/*, returning = "returnedValue"*/)
    public void logAfter(/*Object returnedValue,*/ JoinPoint joinPoint) {
        System.out.println("AFTER: " +
        joinPoint.getTarget().getClass().getSimpleName() + " " +
        joinPoint.getSignature().getName());
//        System.out.println("Returned value: " + returnedValue);
    }

}
