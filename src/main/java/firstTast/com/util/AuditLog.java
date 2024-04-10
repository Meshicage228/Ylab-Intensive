package firstTast.com.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditLog {
    private List<String> logList;

    public AuditLog() {
        this.logList = new ArrayList<>();
    }

    public void addLogEntry(String eventDescription) {
        Date timestamp = new Date();
        String logEntry = timestamp.toString() + ": " + eventDescription;
        logList.add(logEntry);
    }

    public void displayLogs() {
        for (String logEntry : logList) {
            System.out.println(logEntry);
        }
    }

    public static void main(String[] args) {
        AuditLog auditLog = new AuditLog();
        auditLog.addLogEntry("User X added workout Y");
        auditLog.addLogEntry("User Y updated profile information");
        auditLog.displayLogs();
    }
}