package org.example.auditlogaspectstarter.service;

import org.aspectj.lang.ProceedingJoinPoint;

public interface RoleCheckService {
    void adminMethods();

    Object aroundAdminMethods(ProceedingJoinPoint joinPoint) throws Throwable;

    void userLogInMethods();

    Object aroundUserLogInMethods(ProceedingJoinPoint joinPoint) throws Throwable;
}
