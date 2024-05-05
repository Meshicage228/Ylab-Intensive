package first_task.com.annotations.validators;

import first_task.com.annotations.DateFormatCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateFormatExecutor implements ConstraintValidator<DateFormatCheck, String> {
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(DATE_PATTERN);
    }
}
