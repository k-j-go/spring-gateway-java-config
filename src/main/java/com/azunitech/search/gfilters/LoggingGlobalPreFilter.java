package com.azunitech.search.gfilters;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * For Global filter, just declare as component
 */
@Log4j2
@Component
public class LoggingGlobalPreFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global Filter executed");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}