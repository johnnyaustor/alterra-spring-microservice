package com.example.javaspringgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaSpringGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route("customer", r -> r.path("/customers/**").uri("http://localhost:7100"))
                .route("customer", r -> r.path("/customers/**").uri("lb://CUSTOMER"))
//                .route("order", r -> r.path("/orders/**").uri("http://localhost:7200"))
                .route("order", r -> r.path("/orders/**").uri("lb://ORDER"))
                .build();
    }

}
