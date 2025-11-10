spackage com.umland.config;

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
        
        // Converte formato Railway/Heroku para JDBC
        // A URL já contém user:pass@host, então NÃO precisamos passar separadamente
        if (databaseUrl.startsWith("postgresql://")) {
            databaseUrl = "jdbc:" + databaseUrl;
            logger.info("✅ Convertido postgresql:// para jdbc:postgresql://");
        } else if (databaseUrl.startsWith("postgres://")) {
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
            logger.info("✅ Convertido postgres:// para jdbc:postgresql://");
        }
        
        // Remove credenciais da URL para log (segurança)
        String safeUrl = databaseUrl.replaceAll("://([^:]+):([^@]+)@", "://***:***@");
        logger.info("🔗 Conectando em: {}", safeUrl);
        
        // Cria o DataSource APENAS com a URL (que já contém credenciais)
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(databaseUrl);
        
        logger.info("✅ DataSource configurado com sucesso!");
        return dataSourceBuilder.build();
    }
}
