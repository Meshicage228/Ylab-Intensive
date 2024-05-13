/*
package test;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
@ComponentScan("test")
public class TestConfiguration {

    @Bean
    public PostgreSQLContainer<?> postgresContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.3-alpine")
                .withDatabaseName("y_labTest")
                .withUsername("test")
                .withPassword("123");
        container.start();
        return container;
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgresContainer) {
        return new SingleConnectionDataSource(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword(),
                true);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, JdbcTemplate template) {
        template.update("CREATE SCHEMA IF NOT EXISTS " + "service_liquibase");
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("db.changelog/change-logs.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema("service_liquibase");
        return liquibase;
    }
}
*/
