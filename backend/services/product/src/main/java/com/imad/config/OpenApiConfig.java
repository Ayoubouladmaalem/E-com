package com.imad.config;



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
    public OpenAPI productServiceOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8050");
        localServer.setDescription("Serveur de développement local");

        Contact contact = new Contact();
        contact.setEmail("support@ecommerce.com");
        contact.setName("Product Service Team");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Product Service API")
                .version("1.0.0")
                .contact(contact)
                .description("API REST pour la gestion des produits et catégories")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}