package firstTask.com;

import firstTask.com.in.TrainingDiaryApplication;;
import firstTask.com.util.DataBaseConfig;
import firstTask.com.util.TrainingDiaryApplicationFactory;

public class MainClass {
    public static void main(String[] args){
        DataBaseConfig.liquibaseStart();

        TrainingDiaryApplication trainingDiaryApplication = TrainingDiaryApplicationFactory.createTrainingDiaryApplication();
        trainingDiaryApplication.startApplication();
    }
}
