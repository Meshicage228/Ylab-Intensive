package trainingDiary.com.aspect;

import org.example.auditlogaspectstarter.service.RoleCheckService;
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
public class UserRoleCheck implements RoleCheckService {
    private final CurrentUser currentUser;
    private final AuditLogService auditLogService;

    @Pointcut("within(@org.example.auditlogaspectstarter.annotations.AdminAccessCheck *) && execution(* * (..))")
    public void adminMethods() {}

    @Around("adminMethods()")
    public Object aroundAdminMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String role = currentUser.getRole();
        if (isNull(role) || !role.equalsIgnoreCase("admin")) {
            auditLogService.addLogEntry("Access denied: Administrator only", currentUser.getId());
            return new ResponseEntity<>("Access denied: Administrator only", FORBIDDEN);
        }
        auditLogService.addLogEntry("Admin : get all users and workouts", currentUser.getId());
        return joinPoint.proceed();
    }

    @Pointcut("within(@org.example.auditlogaspectstarter.annotations.UserIsLogInCheck *) && execution(* * (..))")
    public void userLogInMethods() {}

    @Around("userLogInMethods()")
    public Object aroundUserLogInMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer id = currentUser.getId();
        if (isNull(id)) {
            auditLogService.addLogEntry("Access denied: You should me log in", null);
            return new ResponseEntity<>("Access denied: You should me log in", FORBIDDEN);
        }
        return joinPoint.proceed();
    }
}