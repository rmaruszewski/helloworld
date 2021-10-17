package com.rmaruszewski.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database-${database.type:postgres}.properties")
class DataSourceConfiguration {
    @Value("${database.driver}")
    private String databaseDriver;

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.password}")
    private String databasePassword;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(databaseDriver);
        dataSourceBuilder.url(databaseUrl);
        dataSourceBuilder.username(databaseUsername);
        dataSourceBuilder.password(databasePassword);

        return dataSourceBuilder.build();
    }
}
