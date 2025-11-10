package com.umland.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    @Primary
    public DataSource dataSource() {
        logger.info("🔧 Configurando DataSource para perfil PROD...");
        
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl == null) {
            logger.error("❌ ERRO: DATABASE_URL não encontrada!");
            logger.error("🔍 Verifique se o banco PostgreSQL está conectado no Railway");
            throw new IllegalStateException(
                "DATABASE_URL não configurada! " +
                "Adicione um banco PostgreSQL no Railway e conecte ao serviço."
            );
        }
        
        logger.info("DATABASE_URL encontrada");
        
        // Parse da URL do Railway: postgresql://user:pass@host:port/database
        String username = null;
        String password = null;
        String jdbcUrl = databaseUrl;
        
        if (databaseUrl.startsWith("postgresql://") || databaseUrl.startsWith("postgres://")) {
            try {
                // Remove o prefixo postgresql:// ou postgres://
                String urlWithoutPrefix = databaseUrl.replaceFirst("^postgres(ql)?://", "");
                
                // Parse: user:pass@host:port/database
                if (urlWithoutPrefix.contains("@")) {
                    String[] parts = urlWithoutPrefix.split("@", 2);
                    String credentials = parts[0];
                    String hostAndDb = parts[1];
                    
                    // Extrai user e pass
                    if (credentials.contains(":")) {
                        String[] creds = credentials.split(":", 2);
                        username = creds[0];
                        password = creds[1];
                    }
                    
                    // Reconstrói a URL JDBC SEM credenciais
                    jdbcUrl = "jdbc:postgresql://" + hostAndDb;
                    
                    logger.info("✅ URL parseada com sucesso");
                    logger.info("👤 Username: {}", username);
                    logger.info("🔐 Password: presente");
                    logger.info("🔗 JDBC URL: {}", jdbcUrl);
                }
            } catch (Exception e) {
                logger.error("❌ Erro ao parsear DATABASE_URL: {}", e.getMessage());
                throw new IllegalStateException("Erro ao parsear DATABASE_URL", e);
            }
        }
        
        // Cria o DataSource com credenciais separadas
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(jdbcUrl);
        
        if (username != null) {
            dataSourceBuilder.username(username);
        }
        if (password != null) {
            dataSourceBuilder.password(password);
        }
        
        logger.info("✅ DataSource configurado com sucesso!");
        return dataSourceBuilder.build();
    }
}
