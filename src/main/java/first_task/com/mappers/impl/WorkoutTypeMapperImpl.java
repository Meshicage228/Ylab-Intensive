package first_task.com.mappers.impl;

import first_task.com.dto.WorkoutTypeDto;
import first_task.com.mappers.WorkoutTypeMapper;
import first_task.com.model.WorkoutType;

import static java.util.Objects.isNull;

public class WorkoutTypeMapperImpl implements WorkoutTypeMapper {

    @Override
    public WorkoutTypeDto toDto(WorkoutType workoutType) {
        if (isNull(workoutType)) {
            return null;
        }

        WorkoutTypeDto workoutTypeDto = new WorkoutTypeDto();
        workoutTypeDto.setType_id(workoutType.getType_id());
        workoutTypeDto.setTypeTitle(workoutType.getTypeTitle());
        return workoutTypeDto;
    }

    @Override
    public WorkoutType toEntity(WorkoutTypeDto workoutTypeDto) {
        if (isNull(workoutTypeDto)) {
            return null;
        }

        WorkoutType workoutType = new WorkoutType();
        workoutType.setType_id(workoutTypeDto.getType_id());
        workoutType.setTypeTitle(workoutTypeDto.getTypeTitle());
        return workoutType;
    }
}
