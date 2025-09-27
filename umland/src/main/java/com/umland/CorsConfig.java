package com.umland;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	System.out.println("🔧 CORS Configuration carregada!"); // ✅ Log de inicialização
            	registry.addMapping("/**") // permite todos os endpoints
		                .allowedOrigins(
		                        "http://localhost:4200",  // ✅ Para desenvolvimento local
		                        "https://hkv62z3p-4200.brs.devtunnels.ms"  // ✅ Para DevTunnels
	                    )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // métodos HTTP
                        .allowedHeaders("*") // cabeçalhos
                        .allowCredentials(true); // permite envio de cookies/autenticação
            }
        };
    }
}
