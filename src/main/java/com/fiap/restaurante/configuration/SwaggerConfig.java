package com.fiap.restaurante.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${api.version}")
    private String versionValue;

    @Bean
    public OpenAPI customSwagger(){
        return new OpenAPI()
                .info(new Info()
                        .title("API de Restaurantes")
                        .description("Documentação para uma API de Restaurante")
                        .version(versionValue)
                        .contact(new Contact()
                                .name("Igor Castro")
                                .email("igorjunqueira19@gmail.com")));
    }
}
