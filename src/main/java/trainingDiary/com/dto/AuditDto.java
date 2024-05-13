package trainingDiary.com.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuditDto {
    private Integer id;
    private String message;
    private String date;
    private Integer userId;
}
