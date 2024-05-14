package trainingDiary.com.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Audit {
    private Integer id;
    private LocalDate timeOfLog;
    private String message;
    private Integer userId;
}
