package trainingDiary.com.annotations;

import javax.validation.Payload;

public @interface UserIsLogInCheck {
    String message() default "User should be logged in";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
