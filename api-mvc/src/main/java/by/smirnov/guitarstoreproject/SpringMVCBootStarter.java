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
@Import({PersistenceProvidersConfiguration.class})
@EnableCaching

public class SpringMVCBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringMVCBootStarter.class, args);
    }

    @Bean
    public ModelMapper modelMapperMVC(){
        return new ModelMapper();
    }

}

