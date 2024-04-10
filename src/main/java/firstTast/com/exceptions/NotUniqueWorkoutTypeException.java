package firstTast.com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotUniqueWorkoutTypeException extends Throwable {
    private String message;
}
