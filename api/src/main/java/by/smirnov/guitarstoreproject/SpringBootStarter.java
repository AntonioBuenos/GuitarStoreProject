package by.smirnov.guitarstoreproject;

import by.smirnov.guitarstoreproject.configuration.PersistenceProvidersConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "by.smirnov.guitarstoreproject")
//@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({PersistenceProvidersConfiguration.class,
/*        WebConfig.class,
        SwaggerConfig.class*/
})
@EnableCaching
/*@OpenAPIDefinition(
        info = @Info(title = "Web Store API", version = "1.0.0"),
        servers = {@Server(url = "http://localhost:8081"), @Server(url = "http://smth.com")}
)*/
@SecurityScheme(name = "JWT Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "Bearer ",
        bearerFormat = "JWT",
        description = "Bearer token for the project.")
public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

/*    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }*/
}

