package com;

import com.in.TrainingDiaryApplication;
import com.util.TrainingDiaryApplicationFactory;

public class MainClass {
    public static void main(String[] args) {
        TrainingDiaryApplication trainingDiaryApplication = TrainingDiaryApplicationFactory.createTrainingDiaryApplication();
        trainingDiaryApplication.startApplication();
    }
}
