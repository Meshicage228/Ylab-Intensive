package trainingDiary.com.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Класс-dto, описывающий логина будущего пользователя
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Schema(
        name = "Login-user dto object",
        description = "Class designed to be accepted from ui"
)
public class LoginUserDto {
    @NotBlank(message = "Введите имя")
    @NotNull(message = "Введите имя")
    @Schema(name = "username", example = "admin", description = "username of user", required = true)
    private String username;
    @Size(min = 2, message = "Пароль от 2 символов")
    @Schema(name = "password", example = "123123", description = "password of user", required = true)
    private String password;
}
