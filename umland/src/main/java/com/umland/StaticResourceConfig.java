package com.umland;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${app.files.path}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("🌐 DEBUG - Configurando static resources para: " + basePath);
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + basePath + "/")
                .setCachePeriod(3600);
    }
}