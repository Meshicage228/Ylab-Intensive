package first_task.com.mappers.impl;

import first_task.com.dto.WorkoutDto;
import first_task.com.mappers.WorkoutMapper;
import first_task.com.mappers.WorkoutTypeMapper;
import first_task.com.model.Workout;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

public class WorkoutMapperImpl implements WorkoutMapper {

    private final WorkoutTypeMapper workoutTypeMapper;

    public WorkoutMapperImpl(WorkoutTypeMapper workoutTypeMapper) {
        this.workoutTypeMapper = workoutTypeMapper;
    }

    @Override
    public WorkoutDto toDto(Workout workout) {
        if (isNull(workout)) {
            return null;
        }

        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setUser_id(workout.getUser_id());
        workoutDto.setTimeOfWorkout(workout.getTimeOfWorkout());
        workoutDto.setWorkoutType(workoutTypeMapper.toDto(workout.getWorkoutType()));
        workoutDto.setMinuteDuration(workout.getMinuteDuration());
        workoutDto.setCaloriesBurned(workout.getCaloriesBurned());
        workoutDto.setAdditionalInfo(workout.getAdditionalInfo());
        workoutDto.setDateOfAdding(workout.getDateOfAdding());
        return workoutDto;
    }

    @Override
    public Workout toEntity(WorkoutDto workoutDto) {
        if (isNull(workoutDto)) {
            return null;
        }

        Workout workout = new Workout();
        workout.setId(workoutDto.getId());
        workout.setUser_id(workoutDto.getUser_id());
        workout.setTimeOfWorkout(workoutDto.getTimeOfWorkout());
        workout.setWorkoutType(workoutTypeMapper.toEntity(workoutDto.getWorkoutType()));
        workout.setMinuteDuration(workoutDto.getMinuteDuration());
        workout.setCaloriesBurned(workoutDto.getCaloriesBurned());
        workout.setAdditionalInfo(workoutDto.getAdditionalInfo());
        workout.setDateOfAdding(workoutDto.getDateOfAdding());
        return workout;
    }

    @Override
    public LinkedList<WorkoutDto> toDtos(List<Workout> workouts) {
        if (isNull(workouts)) {
            return null;
        }

        LinkedList<WorkoutDto> workoutDtos = new LinkedList<>();
        for (Workout workout : workouts) {
            workoutDtos.add(toDto(workout));
        }
        return workoutDtos;
    }

    @Override
    public LinkedList<Workout> toWorkouts(List<WorkoutDto> workoutDtos) {
        if (isNull(workoutDtos)) {
            return null;
        }

        LinkedList<Workout> workouts = new LinkedList<>();
        for (WorkoutDto workoutDto : workoutDtos) {
            workouts.add(toEntity(workoutDto));
        }
        return workouts;
    }
}
