package first_task.com.mappers.impl;

import first_task.com.dto.UserDto;
import first_task.com.mappers.UserMapper;
import first_task.com.mappers.WorkoutMapper;
import first_task.com.model.ConsoleUser;

import static java.util.Objects.isNull;

public class UserMapperImpl implements UserMapper {
    private final WorkoutMapper workoutMapper;

    public UserMapperImpl(WorkoutMapper workoutMapper) {
        this.workoutMapper = workoutMapper;
    }

    @Override
    public UserDto toDto(ConsoleUser consoleUser) {
        if (isNull(consoleUser)) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(consoleUser.getId());
        userDto.setUsername(consoleUser.getUsername());
        userDto.setPassword(consoleUser.getPassword());
        userDto.setRole(consoleUser.getRole());
        userDto.setWorkouts(workoutMapper.toDtos(consoleUser.getWorkouts()));
        return userDto;
    }

    @Override
    public ConsoleUser toEntity(UserDto userDto) {
        if (isNull(userDto)) {
            return null;
        }

        ConsoleUser consoleUser = new ConsoleUser();
        consoleUser.setId(userDto.getId());
        consoleUser.setUsername(userDto.getUsername());
        consoleUser.setPassword(userDto.getPassword());
        consoleUser.setRole(userDto.getRole());
        consoleUser.setWorkouts(workoutMapper.toWorkouts(userDto.getWorkouts()));
        return consoleUser;
    }
}
