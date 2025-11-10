package com.umland.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            // Converte formato Railway para JDBC
            databaseUrl = "jdbc:" + databaseUrl;
        } else if (databaseUrl == null) {
            // Fallback para desenvolvimento
            databaseUrl = "jdbc:postgresql://localhost:5432/umland";
        }
        
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(databaseUrl);
        
        // Railway também injeta essas variáveis separadamente
        String username = System.getenv("PGUSER");
        String password = System.getenv("PGPASSWORD");
        
        if (username != null) {
            dataSourceBuilder.username(username);
        }
        if (password != null) {
            dataSourceBuilder.password(password);
        }
        
        return dataSourceBuilder.build();
    }
}
