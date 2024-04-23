package first_task.com;

import first_task.com.in.TrainingDiaryApplication;
import first_task.com.config.DataBaseConfig;
import first_task.com.util.TrainingDiaryApplicationFactory;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        DataBaseConfig.liquibaseStart();
        Thread.sleep(1000);
        TrainingDiaryApplication trainingDiaryApplication = TrainingDiaryApplicationFactory.createTrainingDiaryApplication();
        trainingDiaryApplication.startApplication();
    }
}
