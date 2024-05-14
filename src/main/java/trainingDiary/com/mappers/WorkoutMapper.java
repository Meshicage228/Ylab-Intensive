package trainingDiary.com.mappers;

import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.model.Workout;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = WorkoutTypeMapper.class
)
public interface WorkoutMapper {

    WorkoutDto toDto(Workout workout);

    Workout toEntity(WorkoutDto workoutDto);


    ArrayList<WorkoutDto> toDtos(List<Workout> workouts);

    ArrayList<Workout> toWorkouts(List<WorkoutDto> workoutDtos);
}
