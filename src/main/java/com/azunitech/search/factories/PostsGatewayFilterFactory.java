package com.azunitech.search.factories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@Log4j2
@Component
public class PostsGatewayFilterFactory extends AbstractGatewayFilterFactory<PostsGatewayFilterFactory.Config> {
    private static Random rnd = new Random();

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            addOriginalRequestUrl(exchange, req.getURI());
            String path = req.getURI()
                    .getRawPath();
            String newPath = path + "/" + String.format("%1d", rnd.nextInt(4));
            log.info("New Path {}", newPath);
            ServerHttpRequest request = req.mutate()
                    .path(newPath)
                    .build();
            exchange.getAttributes()
                    .put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        };
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Config {
        private String newSegment;
    }
}
