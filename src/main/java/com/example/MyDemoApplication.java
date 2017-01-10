package com.example;

import java.util.Arrays;

import com.example.config.SpringApplicationContextInitializer;
import com.example.repositories.UserRepository;
import com.example.users.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MyDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MyDemoApplication.class).
                initializers(new SpringApplicationContextInitializer())
                .application()
                .run(args);
    }

    @Bean
    CommandLineRunner runner(UserRepository crudRepository) {
        return args -> {
            Arrays.asList("Less,More,Even".split(",")).forEach(name -> crudRepository.save(new User(name)));

            crudRepository.findAll().forEach(System.out::println);

            crudRepository.findByName("More").forEach(System.out::println);
        };
    }
}
