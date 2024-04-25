package first_task.com.mappers;

import first_task.com.dto.UserDto;
import first_task.com.model.ConsoleUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        uses = WorkoutMapper.class
)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "workouts", source = "workouts")
    @Mapping(target = "role", source = "role")
    UserDto toDto(ConsoleUser consoleUser);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "workouts", source = "workouts")
    @Mapping(target = "role", source = "role")
    ConsoleUser toEntity(UserDto userDto);
}
