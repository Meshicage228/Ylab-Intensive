package firstTask.com;

import firstTask.com.in.TrainingDiaryApplication;;
import firstTask.com.config.DataBaseConfig;
import firstTask.com.util.TrainingDiaryApplicationFactory;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        DataBaseConfig.liquibaseStart();
        Thread.sleep(1000);
        TrainingDiaryApplication trainingDiaryApplication = TrainingDiaryApplicationFactory.createTrainingDiaryApplication();
        trainingDiaryApplication.startApplication();
    }
}
