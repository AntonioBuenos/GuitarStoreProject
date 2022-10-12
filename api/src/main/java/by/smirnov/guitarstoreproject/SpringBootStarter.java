package by.smirnov.guitarstoreproject;

import by.smirnov.guitarstoreproject.configuration.PersistenceProvidersConfiguration;
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

public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
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

