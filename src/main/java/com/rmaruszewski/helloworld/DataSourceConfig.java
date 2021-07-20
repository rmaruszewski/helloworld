package com.rmaruszewski.helloworld;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        //dataSourceBuilder.url("jdbc:postgresql://helloworld.c4feyg3sqc47.us-east-2.rds.amazonaws.com/helloworld");
        dataSourceBuilder.url("jdbc:postgresql://localhost/helloworld");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");

        return dataSourceBuilder.build();
    }
}
