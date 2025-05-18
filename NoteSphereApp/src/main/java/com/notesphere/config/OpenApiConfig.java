package com.notesphere.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server()
            .url("http://localhost:8080")
            .description("Local Development Server");

        Contact contact = new Contact()
            .name("NoteSphere Team")
            .email("contact@notesphere.com");

        License mitLicense = new License()
            .name("MIT License")
            .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
            .title("NoteSphere API")
            .version("1.0.0")
            .contact(contact)
            .description("API documentation for NoteSphere application")
            .license(mitLicense);

        return new OpenAPI()
            .info(info)
            .servers(List.of(localServer));
    }
} 