package first_task.com.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
@Builder

/**
 * Класс, описывающий пользователя, который использует в приложение в данный момент
 **/

public class CurrentUser {
    private Integer id;
    /** имя пользователя */
    private String username;
    /** Роль пользователя в приложении */
    private String role;
}
