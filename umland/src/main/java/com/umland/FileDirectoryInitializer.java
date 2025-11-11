package com.umland;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class FileDirectoryInitializer implements CommandLineRunner {

    @Value("${app.files.path}")
    private String basePath;

    @Override
    public void run(String... args) {
        List<String> subfolders = List.of("characters", "avatars");

        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
            System.out.println("📂 Pasta base criada: " + baseDir.getAbsolutePath());
        }

        for (String subfolder : subfolders) {
            File subDir = new File(baseDir, subfolder);
            if (!subDir.exists()) {
                subDir.mkdirs();
                System.out.println("📂 Subpasta criada: " + subDir.getAbsolutePath());
            }
        }
    }
}
