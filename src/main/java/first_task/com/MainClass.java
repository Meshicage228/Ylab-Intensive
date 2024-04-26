package first_task.com;


import first_task.com.config.DataBaseConfig;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        DataBaseConfig.liquibaseStart();
    }
}
