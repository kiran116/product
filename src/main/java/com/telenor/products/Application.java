package com.telenor.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * This is the main Spring Boot application class. It configures Spring Boot, JPA, Swagger
 */

@SpringBootApplication
@Configuration
@EnableAutoConfiguration  // Spring Boot Auto Configuration
@ComponentScan(basePackages = "com.telenor.products")
@EnableJpaRepositories("com.telenor.products.dao.jpa")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
