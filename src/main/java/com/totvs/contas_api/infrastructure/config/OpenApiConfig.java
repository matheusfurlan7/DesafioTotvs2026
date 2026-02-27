package com.totvs.contas_api.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Contas a Pagar")
                        .version("1.0.0")
                        .description("Sistema de gestão de contas com importação assíncrona"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório do Projeto"));
    }
}