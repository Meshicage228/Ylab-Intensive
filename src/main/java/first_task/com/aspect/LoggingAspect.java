package first_task.com.aspect;

import first_task.com.util.AuditLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    @Pointcut("within(@first_task.com.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {}

    @Around("annotatedByLoggable()")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) {
        String message = "Calling method : %s";
        AuditLog.addLogEntry(String.format(message, proceedingJoinPoint.getSignature()) ,null);
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(System.out::println);
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Pointcut("within(@first_task.com.annotations.LogWithDuration *) && execution(* * (..))")
    public void annotatedByLoggableWithDuration() {}

    @Around("annotatedByLoggableWithDuration()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String message = "Execution of method : %s finished. Execution time is : %s ms.";
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        AuditLog.addLogEntry(String.format(message, proceedingJoinPoint.getSignature(), end), null);
        return result;
    }
}