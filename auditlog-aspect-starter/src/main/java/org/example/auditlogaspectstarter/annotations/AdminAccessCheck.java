package org.example.auditlogaspectstarter.annotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AdminAccessCheck {
    String message() default "Administrator only";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
