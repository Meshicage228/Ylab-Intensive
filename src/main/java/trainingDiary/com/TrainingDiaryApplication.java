package trainingDiary.com;

import org.example.auditlogaspectstarter.annotations.EnableAuditUserAdminCheck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuditUserAdminCheck
public class TrainingDiaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainingDiaryApplication.class, args);
    }
}
