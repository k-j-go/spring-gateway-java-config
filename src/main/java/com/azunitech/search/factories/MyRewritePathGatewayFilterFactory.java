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

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * using path rewrite
 * original path is posts
 * after rewrite with /posts/1
 * it returns only one
 *
 * using data to start to test
 *
 */


@Component
@Log4j2
public class MyRewritePathGatewayFilterFactory extends AbstractGatewayFilterFactory<MyRewritePathGatewayFilterFactory.Config> {

    public MyRewritePathGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public String name() {
        return "/data";
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            addOriginalRequestUrl(exchange, request.getURI());

            String path = request.getURI().getPath();
            //String newPath = path + "/" + config.getNewSegment();
            String newPath = "/posts/3";
            ServerHttpRequest newRequest = request.mutate().path(newPath).build();
            return chain.filter(exchange.mutate().request(newRequest).build());
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
