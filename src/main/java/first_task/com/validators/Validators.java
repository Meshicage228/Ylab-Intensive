package first_task.com.validators;

import first_task.com.dto.UserDto;
import first_task.com.dto.WorkoutDto;
import first_task.com.dto.WorkoutTypeDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.junit.platform.commons.util.StringUtils.isBlank;

public class Validators {
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    public static List<String> userDtoValidator(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        if (isNull(userDto.getUsername()) || isBlank(userDto.getUsername())) {
            errors.add("Требуется имя");
        } else if (userDto.getUsername().length() > 16) {
            errors.add("имя до 15 сиволов");
        }
        if (isNull(userDto.getPassword()) || isBlank(userDto.getPassword())) {
            errors.add("Требуется пароль");
        } else if (userDto.getPassword().length() < 5) {
            errors.add("Пароль от 5 сиволов");
        }
        return errors;
    }

    public static List<String> workoutValidator(WorkoutDto workoutDto) {
        List<String> errors = new ArrayList<>();

        if (isNull(workoutDto.getMinuteDuration())) {
            errors.add("Требуется длительность тренировки");
        }
        if (workoutDto.getTimeOfWorkout().matches(DATE_PATTERN)) {
            errors.add("Введенная дата не соответствует паттерну YYYY-MM-DD");
        }
        if (isNull(workoutDto.getAdditionalInfo()) || isBlank(workoutDto.getAdditionalInfo())) {
            errors.add("Требуется дополнительная информация");
        } else if (workoutDto.getAdditionalInfo().length() > 50) {
            errors.add("Дополнительная информация до 50 символов");
        }
        if (isNull(workoutDto.getWorkoutType())) {
            errors.add("Отсутствует тип тренировки");
        } else {
            List<String> typeValidator = workoutTypeValidator(workoutDto.getWorkoutType());
            errors.addAll(typeValidator);
        }

        return errors;
    }

    public static List<String> workoutTypeValidator(WorkoutTypeDto workoutDto) {
        List<String> errors = new ArrayList<>();
        if(isNull(workoutDto.getTypeTitle()) || isBlank(workoutDto.getTypeTitle())){
            errors.add("Добавьте тип тренировки");
        };
        return errors;
    }
}
