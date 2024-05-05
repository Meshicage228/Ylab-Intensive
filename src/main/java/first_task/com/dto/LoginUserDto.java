package first_task.com.dto;
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
public class LoginUserDto {
    @NotBlank(message = "Введите имя")
    @NotNull(message = "Введите имя")
    private String username;
    @Size(min = 5, message = "Пароль от 5 символов")
    private String password;
}
