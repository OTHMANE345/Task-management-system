package com.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class apiGatway {

    @Autowired
    public JwtFilter jwtFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("auth-public",
                        r -> r.path("/user/login", "/user/singup", "/user/validate-token")
                                .uri("http://localhost:8081"))
                .route("auth-protected",
                        r -> r.path("/user/**")
                                .filters(f -> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                                .uri("http://localhost:8081"))

                .route("task-protected",
                        r -> r.path("/task/**")
                                .filters(f -> f.filter(jwtFilter.apply(new JwtFilter.Config())))
                                .uri("http://localhost:8082"))
                .build();
    }
}
