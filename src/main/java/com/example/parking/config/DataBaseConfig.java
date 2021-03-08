package com.example.parking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(driverClassName);
        config.setConnectionTestQuery("VALUES 1");
        config.addDataSourceProperty("URL", url);
        config.addDataSourceProperty("user", "sa");
        config.addDataSourceProperty("password", "sa");
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }
}
