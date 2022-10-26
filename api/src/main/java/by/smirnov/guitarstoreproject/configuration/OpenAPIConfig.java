package by.smirnov.guitarstoreproject.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static by.smirnov.guitarstoreproject.configuration.OpenAPIConstants.DESCRIPTION;
import static by.smirnov.guitarstoreproject.configuration.OpenAPIConstants.LICENCE;
import static by.smirnov.guitarstoreproject.configuration.OpenAPIConstants.TITLE;
import static by.smirnov.guitarstoreproject.configuration.OpenAPIConstants.VERSION;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION)
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name(LICENCE);
    }
}
