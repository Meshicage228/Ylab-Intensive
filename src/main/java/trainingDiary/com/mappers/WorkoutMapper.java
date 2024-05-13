package trainingDiary.com.mappers;

import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.model.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
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


    ArrayList<WorkoutDto> toDtos(List<Workout> workouts);

    ArrayList<Workout> toWorkouts(List<WorkoutDto> workoutDtos);
}
