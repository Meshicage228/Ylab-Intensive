package first_task.com.util;

import first_task.com.repository.AuditRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

public class AuditLog {
    private static final AuditRepository repository;

    private AuditLog(){}

    static {
        repository = new AuditRepository();
    }

    public static void addLogEntry(String eventDescription, Integer user_id) {
        repository.saveAudit(eventDescription, user_id);
    }
}
