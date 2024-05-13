package trainingDiary.com.mappers;

import trainingDiary.com.dto.WorkoutTypeDto;
import trainingDiary.com.model.WorkoutType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface WorkoutTypeMapper {

    @Mapping(target = "type_id", source = "type_id")
    @Mapping(target = "typeTitle", source = "typeTitle")
    WorkoutTypeDto toDto(WorkoutType workoutType);

    @Mapping(target = "type_id", source = "type_id")
    @Mapping(target = "typeTitle", source = "typeTitle")
    WorkoutType toEntity(WorkoutTypeDto workoutTypeDto);

    ArrayList<WorkoutTypeDto> toDtos(List<WorkoutType> workoutTypes);
}
