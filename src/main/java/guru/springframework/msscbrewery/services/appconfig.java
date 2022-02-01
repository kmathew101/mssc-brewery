package guru.springframework.msscbrewery.services;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class appconfig {

    @Bean
    Faker getFaker() {
        return new Faker();
    }


}
