package se.maje.scb_movements_backend.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    // Flyway-bean som kan autowireas
    @Bean
    public Flyway flyway(DataSource dataSource) {
        // Konfigurerar Flyway med JDBC-datasource och migrationskatalog
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();

        // Kör migrationerna direkt vid start
        flyway.migrate();

        return flyway;
    }
}
