package FirstTask.com;

import FirstTask.com.in.TrainingDiaryApplication;
import FirstTask.com.config.DataBaseConfig;
import FirstTask.com.util.TrainingDiaryApplicationFactory;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        DataBaseConfig.liquibaseStart();
        Thread.sleep(1000);
        TrainingDiaryApplication trainingDiaryApplication = TrainingDiaryApplicationFactory.createTrainingDiaryApplication();
        trainingDiaryApplication.startApplication();
    }
}
