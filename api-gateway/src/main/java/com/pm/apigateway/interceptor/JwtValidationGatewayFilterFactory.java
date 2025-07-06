package com.pm.apigateway.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient authWebClient;

    public JwtValidationGatewayFilterFactory(
            WebClient.Builder webClientBuilder,
            @Value("{auth.service.url}") String authServiceUrl
    ) {
        this.authWebClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain)->{
            String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
            if(token == null || !token.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return authWebClient.get()
                    .uri("/validate")
                    .header(AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
        };
    }
}
