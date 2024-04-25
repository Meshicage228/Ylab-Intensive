package first_task.com.mappers;

import first_task.com.dto.WorkoutDto;
import first_task.com.model.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedList;
import java.util.List;

@Mapper(
        uses = WorkoutTypeMapper.class
)
public interface WorkoutMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user_id", source = "user_id")
    @Mapping(target = "timeOfWorkout", source = "timeOfWorkout")
    @Mapping(target = "workoutType", source = "workoutType")
    @Mapping(target = "minuteDuration", source = "minuteDuration")
    @Mapping(target = "caloriesBurned", source = "caloriesBurned")
    @Mapping(target = "additionalInfo", source = "additionalInfo")
    @Mapping(target = "dateOfAdding", source = "dateOfAdding")
    WorkoutDto toDto(Workout workout);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user_id", source = "user_id")
    @Mapping(target = "timeOfWorkout", source = "timeOfWorkout")
    @Mapping(target = "workoutType", source = "workoutType")
    @Mapping(target = "minuteDuration", source = "minuteDuration")
    @Mapping(target = "caloriesBurned", source = "caloriesBurned")
    @Mapping(target = "additionalInfo", source = "additionalInfo")
    @Mapping(target = "dateOfAdding", source = "dateOfAdding")
    Workout toEntity(WorkoutDto workoutDto);


    LinkedList<WorkoutDto> toDtos(List<Workout> workouts);

    LinkedList<Workout> toWorkouts(List<WorkoutDto> workoutDtos);
}
