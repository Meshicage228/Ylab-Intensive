package firstTask.com.repository;

import firstTask.com.config.DataBaseConfig;
import firstTask.com.exceptions.NotUniqueTypeTitleException;
import firstTask.com.model.WorkoutType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс - репозиторий, который связывается с базой данных types
 **/
public class WorkoutTypeRepository {
    public ArrayList<WorkoutType> findAll() {
        String query = "SELECT * FROM entities.types";
        ArrayList<WorkoutType> workoutTypes = new ArrayList<>();
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WorkoutType build = WorkoutType.builder()
                        .type_id(resultSet.getInt("type_id"))
                        .typeTitle(resultSet.getString("type"))
                        .build();
                workoutTypes.add(build);
            }
        } catch (SQLException e) {
            System.out.println("Fail to find workout types!");
        }
        return workoutTypes;
    }

    public WorkoutType saveNewType(String newTypeTitle) throws NotUniqueTypeTitleException  {
        for (WorkoutType workoutType : findAll()) {
            if (workoutType.getTypeTitle().equals(newTypeTitle)) {
                throw new NotUniqueTypeTitleException("Not unique workout type!");
            }
        }

        String query = "INSERT INTO entities.types(typeTitle) VALUES (?)";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newTypeTitle);
            preparedStatement.executeUpdate();

            return findByTitle(newTypeTitle);

        } catch (SQLException e) {
            System.out.println("Fail to find workout types!");
        }
        return null;
    }

    public WorkoutType findByTitle(String title) {
        String query = "SELECT * FROM entities.types WHERE typeTitle = ?";

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return WorkoutType.builder()
                        .type_id(resultSet.getInt("type_id"))
                        .typeTitle(resultSet.getString("typeTitle"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println("Fail to find workout type!");
        }
        return null;
    }
}
