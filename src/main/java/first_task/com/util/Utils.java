package first_task.com.util;

/**
 * Класс, создающий Scanner во всем приложении
 */
public class Utils {
    public static String formJsonErrorMessage(String message) {
        String errorMessage = "{\"error\": \"%s\"}";
        return String.format(errorMessage, message);
    }
}