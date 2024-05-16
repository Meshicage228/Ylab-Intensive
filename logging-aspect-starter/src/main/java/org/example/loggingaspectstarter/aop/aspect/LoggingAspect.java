package org.example.loggingaspectstarter.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(@org.example.loggingaspectstarter.aop.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {}

    @After("annotatedByLoggable()")
    public void log(JoinPoint joinPoint) {
        String message = "Calling method : %s";
        System.out.println(String.format(message, joinPoint.getSignature()));
        Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
    }

    @Pointcut("within(@org.example.loggingaspectstarter.aop.annotations.LogWithDuration *) && execution(* *(..))")
    public void annotatedByLoggableWithDuration() {}

    @Around("annotatedByLoggableWithDuration()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String message = "Execution of method : %s finished. Execution time is : %s ms.";
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println(String.format(message, proceedingJoinPoint.getSignature(), end));
        return result;
    }
}