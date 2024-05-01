package first_task.com.repository;

import first_task.com.config.DataBaseConfig;
import first_task.com.dto.WorkoutTypeDto;
import first_task.com.exceptions.NotUniqueTypeTitleException;
import first_task.com.model.WorkoutType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static first_task.com.util.SQLUtilQueries.*;

/**
 * Класс-репозиторий, ответственный за соединение с бд types
 **/
public class WorkoutTypeRepository {
    public ArrayList<WorkoutType> findAll() {
        ArrayList<WorkoutType> workoutTypes = new ArrayList<>();
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TYPES_GET_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WorkoutType build = WorkoutType.builder()
                        .type_id(resultSet.getInt("type_id"))
                        .typeTitle(resultSet.getString("type"))
                        .build();
                workoutTypes.add(build);
            }
        } catch (SQLException e) {
            new RuntimeException("Fail while finding workout types", e);
        }
        return workoutTypes;
    }

    public WorkoutType saveNewType(WorkoutTypeDto workoutTypeDto) throws NotUniqueTypeTitleException  {
        for (WorkoutType workoutType : findAll()) {
            if (workoutType.getTypeTitle().toLowerCase().equals(workoutTypeDto.getTypeTitle().toLowerCase())) {
                throw new NotUniqueTypeTitleException("Not unique workout type!");
            }
        }

        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TYPE)) {

            preparedStatement.setString(1, workoutTypeDto.getTypeTitle().toUpperCase());
            preparedStatement.executeUpdate();

            return findByTitle(workoutTypeDto.getTypeTitle());

        } catch (SQLException e) {
            new RuntimeException("Fail to save new workout type!");
        }
        return null;
    }

    public WorkoutType findByTitle(String title) {
        try (Connection connection = DataBaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TYPE_FIND_BY_TITLE)) {
            preparedStatement.setString(1, title.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return WorkoutType.builder()
                        .type_id(resultSet.getInt("type_id"))
                        .typeTitle(resultSet.getString("type"))
                        .build();
            }
        } catch (SQLException e) {
            new RuntimeException("Fail while finding workout type by title!");
        }
        return null;
    }
}