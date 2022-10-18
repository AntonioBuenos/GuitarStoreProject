package by.smirnov.guitarstoreproject;

import by.smirnov.guitarstoreproject.configuration.OpenAPIConfig;
import by.smirnov.guitarstoreproject.configuration.PersistenceProvidersConfiguration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication(scanBasePackages = "by.smirnov.guitarstoreproject")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({PersistenceProvidersConfiguration.class, OpenAPIConfig.class})
@EnableCaching
@SecurityScheme(name = "JWT Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Bearer token for the project.")
public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE)
/*                .setPropertyCondition(context ->
                        !(context.getSource() instanceof PersistentCollection))*/
        ;
        return mapper;
    }
}

