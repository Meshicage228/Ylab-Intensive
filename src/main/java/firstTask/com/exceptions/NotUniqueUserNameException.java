package firstTask.com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Класс ответственен за обязательную обработку события неуникальности пользовательского имени
 * @author  Zherko Vladislav
 * @since   1.0
 */
@AllArgsConstructor
@Getter
public class NotUniqueUserNameException extends Exception {
    private String message;
}
