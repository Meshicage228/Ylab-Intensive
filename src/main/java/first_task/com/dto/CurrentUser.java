package first_task.com.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
@Builder
public class CurrentUser {
    private Integer id;
    /** имя пользователя */
    private String username;
    /** Роль пользователя в приложении */
    private String role;
}
