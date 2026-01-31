package com.apigateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ObjectInputFilter;
import java.util.Collections;
import java.util.Map;


@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    @Autowired
    private WebClient webClient;

    public JwtFilter() {
       super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
           String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization") ;
           if(authHeader != null && authHeader.startsWith("Bearer ")){
               String token = authHeader.substring(7);
               return validateToken(token).flatMap(
                       res -> {
                           ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("USER-ID", res.get("id").toString())
                                   .header("USER-ROLE",  res.get("role").toString())
                                   .header("EMAIL",  res.get("email").toString())
                                   .build();
                           return chain.filter(exchange.mutate().request(mutatedRequest).build());
                       }
               ).onErrorResume( e -> {
                   exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                   return exchange.getResponse().setComplete();
               });
           }
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }

    private Mono<Map> validateToken(String token){
        return webClient.post()
                .uri("/user/validate-token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(Collections.singletonMap("token", token))
                .retrieve()
                .bodyToMono(Map.class)
                .map(res ->
                {
                    if (res != null && res.containsKey("id")) {
                        return res;
                    }
                    throw new RuntimeException("Invalid token response - missing id");
                });
    }

    public static class Config {

    }
}
