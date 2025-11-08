package com.umland.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${app.db.restore-on-startup:true}")
    private boolean restoreOnStartup;

    @Override
    public void run(String... args) {
        if (!restoreOnStartup) {
            logger.info("Database restore on startup is disabled");
            return;
        }

        try {
            logger.info("========================================");
            logger.info("Starting database restore...");
            logger.info("========================================");
            
            // Extrair informações da URL do banco
            String dbName = extractDatabaseName(databaseUrl);
            String dbHost = extractHost(databaseUrl);
            String dbPort = extractPort(databaseUrl);

            logger.info("Database: {}", dbName);
            logger.info("Host: {}", dbHost);
            logger.info("Port: {}", dbPort);
            logger.info("User: {}", dbUsername);

            // Tentar restaurar usando SQL primeiro (mais compatível)
            File tempFile = null;
            try {
                logger.info("Attempting to restore from SQL file...");
                tempFile = copySqlToTempFile();
                logger.info("SQL file copied to: {}", tempFile.getAbsolutePath());

                // Dropar e recriar o banco de dados
                dropAndRecreateDatabase(dbHost, dbPort, dbName);

                // Restaurar usando SQL
                restoreDatabaseFromSql(dbHost, dbPort, dbName, tempFile);

                logger.info("Database restored successfully from SQL file!");

            } catch (Exception sqlException) {
                logger.warn("Failed to restore from SQL file: {}", sqlException.getMessage());
                logger.info("Attempting to restore from binary dump file...");

                try {
                    // Se falhar, tentar com o dump binário
                    if (tempFile != null && tempFile.exists()) {
                        if (!tempFile.delete()) {
                            logger.warn("Failed to delete temporary SQL file: {}", tempFile.getAbsolutePath());
                        }
                    }
                    
                    tempFile = copyDumpToTempFile();
                    logger.info("Dump file copied to: {}", tempFile.getAbsolutePath());

                    // Dropar e recriar o banco de dados novamente
                    dropAndRecreateDatabase(dbHost, dbPort, dbName);

                    // Restaurar o dump
                    restoreDatabase(dbHost, dbPort, dbName, tempFile);

                    logger.info("Database restored successfully from dump file!");
                    
                } catch (Exception dumpException) {
                    logger.error("Failed to restore from dump file as well: {}", dumpException.getMessage());
                    throw dumpException; // Re-lançar para ser capturado pelo catch externo
                }
            } finally {
                // Limpar arquivo temporário
                if (tempFile != null && tempFile.exists()) {
                    if (!tempFile.delete()) {
                        logger.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath());
                    }
                }
            }

            logger.info("========================================");
            logger.info("Database restore completed successfully!");
            logger.info("========================================");

        } catch (Exception e) {
            logger.error("========================================");
            logger.error("Failed to restore database", e);
            logger.error("========================================");
            logger.error("The application will continue, but the database may not be in the expected state.");
            logger.error("Please check:");
            logger.error("  1. PostgreSQL is running");
            logger.error("  2. Credentials in application.properties are correct");
            logger.error("  3. psql and pg_restore are in the system PATH");
            logger.error("  4. The dump/SQL files are compatible with your PostgreSQL version");
            // Não lançar exceção para não impedir o startup da aplicação
        }
    }

    private String extractDatabaseName(String url) {
        // jdbc:postgresql://localhost:5432/umland
        String[] parts = url.split("/");
        String dbNameWithParams = parts[parts.length - 1];
        return dbNameWithParams.split("\\?")[0];
    }

    private String extractHost(String url) {
        // jdbc:postgresql://localhost:5432/umland
        String withoutProtocol = url.split("//")[1];
        String hostPort = withoutProtocol.split("/")[0];
        return hostPort.split(":")[0];
    }

    private String extractPort(String url) {
        // jdbc:postgresql://localhost:5432/umland
        String withoutProtocol = url.split("//")[1];
        String hostPort = withoutProtocol.split("/")[0];
        String[] parts = hostPort.split(":");
        return parts.length > 1 ? parts[1] : "5432";
    }

    private File copyDumpToTempFile() throws IOException {
        ClassPathResource dumpResource = new ClassPathResource("umland.dump");
        Path tempFile = Files.createTempFile("umland_dump_", ".dump");
        Files.copy(dumpResource.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toFile();
    }

    private File copySqlToTempFile() throws IOException {
        ClassPathResource sqlResource = new ClassPathResource("umland.sql");
        Path tempFile = Files.createTempFile("umland_sql_", ".sql");
        Files.copy(sqlResource.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toFile();
    }

    private void dropAndRecreateDatabase(String host, String port, String dbName) throws IOException, InterruptedException {
        logger.info("----------------------------------------");
        logger.info("Dropping and recreating database '{}'...", dbName);
        logger.info("----------------------------------------");

        // Primeiro, terminar todas as conexões ativas ao banco de dados
        logger.info("Terminating active connections to database '{}'...", dbName);
        String[] terminateCommand = {
            "cmd.exe", "/c",
            String.format("psql -h %s -p %s -U %s -d postgres -c \"SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '%s' AND pid <> pg_backend_pid();\"",
                    host, port, dbUsername, dbName)
        };
        executeCommand(terminateCommand, dbPassword);
        logger.info("Connections terminated");

        // Aguardar um pouco para garantir que as conexões foram encerradas
        Thread.sleep(1000);

        // Conectar ao banco postgres (padrão) para dropar/criar o banco umland
        String[] dropCommand = {
            "cmd.exe", "/c",
            String.format("psql -h %s -p %s -U %s -d postgres -c \"DROP DATABASE IF EXISTS %s;\"",
                    host, port, dbUsername, dbName)
        };

        String[] createCommand = {
            "cmd.exe", "/c",
            String.format("psql -h %s -p %s -U %s -d postgres -c \"CREATE DATABASE %s;\"",
                    host, port, dbUsername, dbName)
        };

        // Executar DROP
        logger.info("Dropping database if exists...");
        executeCommand(dropCommand, dbPassword);
        logger.info("Drop command completed");
        
        // Executar CREATE
        logger.info("Creating database...");
        executeCommand(createCommand, dbPassword);
        logger.info("Create command completed");
    }

    private void restoreDatabase(String host, String port, String dbName, File dumpFile) throws IOException, InterruptedException {
        logger.info("----------------------------------------");
        logger.info("Restoring database from dump file...");
        logger.info("----------------------------------------");

        String[] restoreCommand = {
            "cmd.exe", "/c",
            String.format("pg_restore -h %s -p %s -U %s -d %s -v \"%s\"",
                    host, port, dbUsername, dbName, dumpFile.getAbsolutePath())
        };

        executeCommand(restoreCommand, dbPassword);
        logger.info("Restore command completed");
    }

    private void restoreDatabaseFromSql(String host, String port, String dbName, File sqlFile) throws IOException, InterruptedException {
        logger.info("----------------------------------------");
        logger.info("Restoring database from SQL file...");
        logger.info("----------------------------------------");

        String[] restoreCommand = {
            "cmd.exe", "/c",
            String.format("psql -h %s -p %s -U %s -d %s -f \"%s\"",
                    host, port, dbUsername, dbName, sqlFile.getAbsolutePath())
        };

        executeCommand(restoreCommand, dbPassword);
        logger.info("SQL restore command completed");
    }

    private void executeCommand(String[] command, String password) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        
        // Definir variável de ambiente para senha
        processBuilder.environment().put("PGPASSWORD", password);
        
        // Redirecionar saída
        processBuilder.redirectErrorStream(true);
        
        Process process = processBuilder.start();

        // Ler saída do comando
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                // Log apenas linhas importantes (não spam)
                if (!line.trim().isEmpty()) {
                    logger.info("  > {}", line);
                }
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String errorMessage = String.format("Command exited with code: %d", exitCode);
            if (output.toString().contains("error") || output.toString().contains("ERROR")) {
                throw new IOException(errorMessage + "\nOutput: " + output);
            } else {
                logger.warn("{} (this may be normal for some operations)", errorMessage);
            }
        }
    }
}

