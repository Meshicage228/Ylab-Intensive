package firstTast.com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotUniqueUserNameException extends Exception {
    private String message;
}
