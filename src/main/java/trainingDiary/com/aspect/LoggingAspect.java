package trainingDiary.com.aspect;

import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@RequiredArgsConstructor
@Component
public class LoggingAspect {

    private final AuditLogService auditLogService;
    private final CurrentUser appUser;

    @Pointcut("within(@trainingDiary.com.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {}

    @After("annotatedByLoggable()")
    public void log(JoinPoint joinPoint) {
        String message = "Calling method : %s";
        auditLogService.addLogEntry(String.format(message, joinPoint.getSignature()) , appUser.getId());
        Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
    }

    @Pointcut("within(@trainingDiary.com.annotations.LogWithDuration *) && execution(* *(..))")
    public void annotatedByLoggableWithDuration() {}

    @Around("annotatedByLoggableWithDuration()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String message = "Execution of method : %s finished. Execution time is : %s ms.";
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        auditLogService.addLogEntry(String.format(message, proceedingJoinPoint.getSignature(), end), appUser.getId());
        return result;
    }
}