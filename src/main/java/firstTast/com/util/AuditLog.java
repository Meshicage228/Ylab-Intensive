package firstTast.com.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditLog {
    private ArrayList<String> logList;

    public AuditLog() {
        this.logList = new ArrayList<>();
    }

    public void addLogEntry(String eventDescription) {
        Date timestamp = new Date();
        String logEntry = timestamp.toString() + ": " + eventDescription;
        logList.add(logEntry);
        System.out.println(logEntry);
    }

    public ArrayList<String> displayLogs() {
        return logList;
    }
}