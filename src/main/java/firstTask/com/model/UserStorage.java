package firstTask.com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


/** Класс хранит (имитирует) хранение всех пользователей, подобно базе данных
 * @see ConsoleUser - пользователь приложения
 * */
public class UserStorage {
    /** имитатор базы данных, который можно вызвать в любой точке программы */
    @Getter
    @Setter
    public static ArrayList<ConsoleUser> allUsers = new ArrayList<>();
}
