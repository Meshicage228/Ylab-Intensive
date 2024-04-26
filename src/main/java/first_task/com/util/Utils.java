package first_task.com.util;

import java.util.Scanner;

import static java.util.Objects.isNull;

/**
 * Класс, создающий Scanner во всем приложении
 */
public class Utils {
    public static String formJsonErrorMessage(String message) {
        String errorMessage = "{\"error\": \"%s\"}";
        return String.format(errorMessage, message);
    }
}
