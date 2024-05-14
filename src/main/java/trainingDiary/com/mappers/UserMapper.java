package trainingDiary.com.mappers;

import trainingDiary.com.dto.UserDto;
import trainingDiary.com.model.AppUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = WorkoutMapper.class
)
public interface UserMapper {

    UserDto toDto(AppUser appUser);

    AppUser toEntity(UserDto userDto);

    List<UserDto> toDtos(List<AppUser> appUsers);
}
