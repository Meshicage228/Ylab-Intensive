package trainingDiary.com.service;

import org.example.loggingaspectstarter.aop.annotations.LogWithDuration;
import trainingDiary.com.dto.AuditDto;
import trainingDiary.com.mappers.AuditLogMapper;
import trainingDiary.com.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditRepository repository;
    private final AuditLogMapper auditLogMapper;

    @LogWithDuration
    public void addLogEntry(String eventDescription, Integer user_id) {
        repository.saveAudit(eventDescription, user_id);
    }

    public ArrayList<AuditDto> getLogEntries() {
        return auditLogMapper.toDtos(repository.getAllAuditLogs());
    }
}
