package trainingDiary.com.repository;

import trainingDiary.com.dto.WorkoutTypeDto;
import trainingDiary.com.exceptions.NotUniqueTypeTitleException;
import trainingDiary.com.mappers.rowMappers.WorkoutTypeRowMapper;
import trainingDiary.com.model.WorkoutType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static trainingDiary.com.util.SQLUtilQueries.*;

/**
 * Класс-репозиторий, ответственный за соединение с бд types
 **/

@Repository
@RequiredArgsConstructor
public class WorkoutTypeRepository {
    private final JdbcTemplate template;
    private final WorkoutTypeRowMapper rowMapper;

    public ArrayList<WorkoutType> findAll() {
       return (ArrayList<WorkoutType>) template.query(TYPES_GET_ALL, rowMapper);
    }

    public WorkoutType saveNewType(WorkoutTypeDto workoutTypeDto) throws NotUniqueTypeTitleException  {
        for (WorkoutType workoutType : findAll()) {
            if (workoutType.getTypeTitle().toLowerCase().equals(workoutTypeDto.getTypeTitle().toLowerCase())) {
                throw new NotUniqueTypeTitleException("Not unique workout type!");
            }
        }
        template.update(SAVE_TYPE, workoutTypeDto.getTypeTitle().toUpperCase());

        return findByTitle(workoutTypeDto.getTypeTitle().toUpperCase());
    }

    public WorkoutType findByTitle(String title) {
        return template.query(TYPE_FIND_BY_TITLE, new Object[]{title}, rowMapper).get(0);
    }
}
