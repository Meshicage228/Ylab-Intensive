package first_task.com.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import java.util.Arrays;

@Aspect
public class LoggingAspect {

    @Around("execution(* *(..)) && @annotation(first_task.com.annotations.Loggable)")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        System.out.println("with args:");
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(System.out::println);
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @Around("execution(* *(..)) && @annotation(first_task.com.annotations.LogWithDuration)")
    public Object logWithTime(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        System.out.print("with args:");
        Arrays.stream(proceedingJoinPoint.getArgs()).forEach(System.out::println);
        long start = System.currentTimeMillis();
        Object result;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("Duration: " + dur + "ms");
        return result;
    }
}