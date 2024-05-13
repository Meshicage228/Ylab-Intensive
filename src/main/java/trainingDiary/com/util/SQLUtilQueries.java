package trainingDiary.com.util;

public class SQLUtilQueries {

    private SQLUtilQueries() {
    }

    public static final String FIND_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM entities.users WHERE username = ? AND password = ?";
    public static final String FIND_ALL_USERS = """
            SELECT username, us.user_id, role, password FROM entities.workouts as w 
            LEFT JOIN entities.types as tp ON w.workout_type_id = tp.type_id
            LEFT JOIN entities.users as us ON w.user_id = us.user_id
            """;
    public static final String SAVE_AUDIT = "INSERT INTO audit_log.audit_logs (time_of_action, action_description, user_id) VALUES(?,?,?)";
    public static final String SAVE_USER = "INSERT INTO entities.users (username, password, role) VALUES (?,?,?)";
    public static final String FIND_USER_BY_USERNAME = "SELECT * FROM entities.users WHERE username = ?";
    public static final String GET_WORKOUT_BY_USER_ID = "SELECT * FROM entities.workouts as w LEFT JOIN entities.types as tp on w.workout_type_id = tp.type_id WHERE user_id = ?";
    public static final String GET_WORKOUT_BY_WORKOUT_ID = "SELECT * FROM entities.workouts as w LEFT JOIN entities.types as tp on w.workout_type_id = tp.type_id WHERE workout_id = ?";
    public static final String SAVE_WORKOUT = "INSERT INTO entities.workouts (user_id, training_date_creation, adding_date, additional_info, workout_type_id, calories_burned, minute_duration)" +
            " VALUES(?,?,?,?,?,?,?)";
    public static final String UPDATE_WORKOUT_MINUTES = "UPDATE entities.workouts SET minute_duration = ? WHERE workout_id = ? AND user_id = ?";
    public static final String UPDATE_WORKOUT_CALORIES = "UPDATE entities.workouts SET calories_burned = ? WHERE workout_id = ? AND user_id = ?";
    public static final String UPDATE_WORKOUT_ADDITIONAL = "UPDATE entities.workouts SET additional_info = ? WHERE workout_id = ? AND user_id = ?";
    public static final String UPDATE_WORKOUT_DATE = "UPDATE entities.workouts SET adding_date = ? WHERE workout_id = ? AND user_id = ?";
    public static final String WORKOUT_DELETE = "DELETE FROM entities.workouts WHERE workout_id = ? AND user_id = ?";
    public static final String TYPES_GET_ALL = "SELECT * FROM entities.types";
    public static final String SAVE_TYPE = "INSERT INTO entities.types (type) VALUES (?)";
    public static final String TYPE_FIND_BY_TITLE = "SELECT * FROM entities.types WHERE type = ?";

}
