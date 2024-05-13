package trainingDiary.com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс ответственен за обязательную обработку события неуникальности типа тренировки во время сохранения
 * @author  Zherko Vladislav
 * @since   1.0
 */
@AllArgsConstructor
@Getter
public class NotUniqueWorkoutException extends Throwable {
    private String message;
}
