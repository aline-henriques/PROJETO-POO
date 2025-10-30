package br.com.cachacaria_gomes.gerenciadorweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull; // NOVA IMPORTAÇÃO
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            // O parâmetro CorsRegistry deve ser anotado com @NonNull para evitar erros de compilação no Spring 6+
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        // Limita apenas ao seu frontend de desenvolvimento
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD") // Adicionamos HEAD
                        .allowedHeaders("*") // Permite todos os headers
                        .allowCredentials(true); // Permite o envio de cookies/autenticação
            }
        };
    }
}
