package org.example.auditlogaspectstarter.annotations;

import org.example.auditlogaspectstarter.AuditlogAspectStarterApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AuditlogAspectStarterApplication.class)
public @interface EnableAuditUserAdminCheck {
}
