package firstTask.com.util;

import java.util.Scanner;

import static java.util.Objects.isNull;

/**
 * Класс, создающий Scanner во всем приложении
 */
public class UtilScanner {
    private static Scanner scanner;
    public static Scanner getInstance() {
        if(isNull(scanner)) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
