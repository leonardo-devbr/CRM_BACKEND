package com.crm.cliente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI crmOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("CRM REST API")

                        .description("API desenvolvida em Java Spring Boot")

                        .version("1.0")

                        .contact(new Contact()

                                .name("Leonardo")

                                .email("leo@gmail.com")));
    }

}