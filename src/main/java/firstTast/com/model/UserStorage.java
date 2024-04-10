package firstTast.com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class UserStorage {
    @Getter
    @Setter
    private static ArrayList<ConsoleUser> allUsers = new ArrayList<>();
}
