package com.rmaruszewski.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
class DataSourceConfiguration {
    @Value( "${database.endpoint}" )
    private String databaseEndpoint;

    @Value( "${database.name}" )
    private String databaseName;

    @Value( "${database.username}" )
    private String databaseUsername;

    @Value( "${database.password}" )
    private String databasePassword;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        //dataSourceBuilder.url("jdbc:postgresql://helloworld.c4feyg3sqc47.us-east-2.rds.amazonaws.com/helloworld");
        dataSourceBuilder.url("jdbc:postgresql://" + databaseEndpoint + '/' + databaseName);
        dataSourceBuilder.username(databaseUsername);
        dataSourceBuilder.password(databasePassword);

        return dataSourceBuilder.build();
    }
}
