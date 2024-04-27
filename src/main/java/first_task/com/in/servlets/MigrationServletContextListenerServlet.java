package first_task.com.in.servlets;

import first_task.com.config.DataBaseConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Запуск liquibase и подключение к бд
 **/
@WebListener
public class MigrationServletContextListenerServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DataBaseConfig.liquibaseStart();
    }
}
