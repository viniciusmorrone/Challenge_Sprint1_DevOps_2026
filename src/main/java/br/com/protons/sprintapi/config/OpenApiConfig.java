package br.com.protons.sprintapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // Configura título, versão e descrição que aparecem na página do Swagger.
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Atomo+ - API do Quiz")
                .version("1.0.0")
                .description("API REST do quiz educativo sobre aplicacoes da tecnologia nuclear " + "(Challenge CTMSP 2026 - Sprint 1)"));
    }
}
