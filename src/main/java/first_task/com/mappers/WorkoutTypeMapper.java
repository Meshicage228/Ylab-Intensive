package first_task.com.mappers;

import first_task.com.dto.WorkoutTypeDto;
import first_task.com.model.WorkoutType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface WorkoutTypeMapper {

    @Mapping(target = "type_id", source = "type_id")
    @Mapping(target = "typeTitle", source = "typeTitle")
    WorkoutTypeDto toDto(WorkoutType workoutType);

    @Mapping(target = "type_id", source = "type_id")
    @Mapping(target = "typeTitle", source = "typeTitle")
    WorkoutType toEntity(WorkoutTypeDto workoutTypeDto);

    ArrayList<WorkoutTypeDto> toDtos(List<WorkoutType> workoutTypes);
}
