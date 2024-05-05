package first_task.com.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
