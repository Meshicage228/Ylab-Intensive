package first_task.com.dto;

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
    private String username;
    private String password;
}
