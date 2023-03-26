package com.azunitech.search.factories;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {
    public MyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("AbstractGatewayFilterFactory MyFilterFactory executed");
        return (exchange, chain) -> chain.filter(exchange);
    }

    public static class Config {
        // Add configuration properties for your filter here
    }
}