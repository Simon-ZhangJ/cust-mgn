package com.simon.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

//@Configuration
public class DatasourceConfig {
    
    @Profile("Developement")
    @Bean
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:dev-schema.sql")
            .addScript("classpath:dev-data.sql")
            .build();
    }
    
    @Profile("Production")
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:prod-schema.sql")
        .addScript("classpath:prod-data.sql")
        .build();
    }
}
