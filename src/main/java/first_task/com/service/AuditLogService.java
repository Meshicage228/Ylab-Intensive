package first_task.com.service;

import first_task.com.annotations.LogWithDuration;
import first_task.com.repository.AuditRepository;
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
