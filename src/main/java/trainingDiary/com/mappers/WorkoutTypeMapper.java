package trainingDiary.com.mappers;

import trainingDiary.com.dto.WorkoutTypeDto;
import trainingDiary.com.model.WorkoutType;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface WorkoutTypeMapper {

    WorkoutTypeDto toDto(WorkoutType workoutType);

    WorkoutType toEntity(WorkoutTypeDto workoutTypeDto);

    ArrayList<WorkoutTypeDto> toDtos(List<WorkoutType> workoutTypes);
}
