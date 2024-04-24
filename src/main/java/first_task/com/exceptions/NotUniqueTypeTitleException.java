package first_task.com.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс ответственен за обязательную обработку события неуникальности типа тренировки
 * @author  Zherko Vladislav
 * @since   1.0
 */

@AllArgsConstructor
@Getter
public class NotUniqueTypeTitleException extends Exception {
    private String message;
}
