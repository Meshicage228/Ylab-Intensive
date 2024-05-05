package first_task.com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

/**
 * Класс ответственен за выдачу указаний о неверно введенных данных
 * @author  Zherko Vladislav
 * @since   1.0
 */

@Getter
@AllArgsConstructor
public class InappropriateDataException extends Exception {
    private BindingResult result;
}
