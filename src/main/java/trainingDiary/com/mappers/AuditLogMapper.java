package trainingDiary.com.mappers;

import org.mapstruct.Mapper;
import trainingDiary.com.dto.AuditDto;
import trainingDiary.com.dto.UserDto;
import trainingDiary.com.model.AppUser;
import trainingDiary.com.model.Audit;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface AuditLogMapper {
    AuditDto toDto(Audit audit);

    Audit toEntity(AuditDto auditDto);

    ArrayList<AuditDto> toDtos(List<Audit> audits);
}
