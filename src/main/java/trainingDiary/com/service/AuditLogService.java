package trainingDiary.com.service;

import trainingDiary.com.annotations.LogWithDuration;
import trainingDiary.com.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditRepository repository;

    @LogWithDuration
    public void addLogEntry(String eventDescription, Integer user_id) {
        repository.saveAudit(eventDescription, user_id);
    }
}
