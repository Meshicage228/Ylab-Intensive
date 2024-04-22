package firstTask.com.util;

import firstTask.com.repository.AuditRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiredArgsConstructor
public class AuditLog {
    private ArrayList<String> logList = new ArrayList<>();
    @NonNull
    private AuditRepository repository;

    public void addLogEntry(String eventDescription, Integer user_id) {
        repository.saveAudit(eventDescription, user_id);
        String logEntry = LocalDate.now().toString() + ": " + eventDescription;
        logList.add(logEntry);
        System.out.println(logEntry);
    }
}
