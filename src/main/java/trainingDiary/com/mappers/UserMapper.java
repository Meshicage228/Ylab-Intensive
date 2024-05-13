package trainingDiary.com.mappers;

import trainingDiary.com.dto.UserDto;
import trainingDiary.com.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = WorkoutMapper.class
)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "workouts", source = "workouts")
    @Mapping(target = "role", source = "role")
    UserDto toDto(AppUser appUser);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "workouts", source = "workouts")
    @Mapping(target = "role", defaultValue = "USER")
    AppUser toEntity(UserDto userDto);

    List<UserDto> toDtos(List<AppUser> appUsers);
}
