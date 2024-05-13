package trainingDiary.com.aspect;

import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminCheckAspect {
    private final CurrentUser currentUser;
    private final AuditLogService auditLogService;

    @Pointcut("within(@trainingDiary.com.annotations.AdminAccessCheck *) && execution(* * (..))")
    public void adminMethods() {}

    @Around("adminMethods()")
    public Object aroundAdminMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        if(isNull(currentUser.getRole())){
            auditLogService.addLogEntry("Access denied: Administrator only", currentUser.getId());
            return new ResponseEntity<>("Access denied: Administrator only", FORBIDDEN);
        }
        else if(!currentUser.getRole().toLowerCase().equals("admin")){
            auditLogService.addLogEntry("Access denied: Administrator only", currentUser.getId());
            return new ResponseEntity<>("Access denied: Administrator only", FORBIDDEN);
        };
        auditLogService.addLogEntry("Admin : get all users and workouts", currentUser.getId());
        return joinPoint.proceed();
    }
}