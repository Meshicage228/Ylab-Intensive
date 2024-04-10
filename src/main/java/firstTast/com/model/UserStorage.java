package firstTast.com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserStorage {
    private ArrayList<ConsoleUser> allUsers = new ArrayList<>();
}
