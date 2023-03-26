package com.azunitech.search;

import com.azunitech.search.factories.MyGatewayFilterFactory;
import com.azunitech.search.factories.MyRewritePathGatewayFilterFactory;
import com.azunitech.search.factories.MyRoutePredicateFactory;
import com.azunitech.search.factories.PostsGatewayFilterFactory;
import com.azunitech.search.filters.MyGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RouteConfig {
    @Autowired
    MyGatewayFilter myGatewayFilter;

    @Autowired
    MyGatewayFilterFactory myFilterFactory;

    @Autowired
    MyRoutePredicateFactory myRoutePredicateFactory;

    @Autowired
    MyRewritePathGatewayFilterFactory myRewritePathGatewayFilterFactory;

    @Autowired
    PostsGatewayFilterFactory postsGatewayFilterFactory;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("get_route", r -> r.path("/get")
                        //Add predicate factory
                        .and()
                        .predicate(myRoutePredicateFactory.apply(new MyRoutePredicateFactory.Config("get")))
                        //Pre-post filters
                        .filters
                                (f -> f.filter(myGatewayFilter, 0)
                                        .filter(myFilterFactory.apply(new MyGatewayFilterFactory.Config()))
                                        .addRequestHeader("first-request", "first-request-header")
                                        .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://httpbin.org"))
                .route("get_route-data", r -> r.path("/data")
                        //Add predicate factory
                        .and()
                        .predicate(myRoutePredicateFactory.apply(new MyRoutePredicateFactory.Config("data")))
                        //Pre-post filters
                        .filters
                                (f -> {
                                            RetryGatewayFilterFactory.BackoffConfig bConfig = new RetryGatewayFilterFactory.BackoffConfig();
                                            bConfig.setMaxBackoff(Duration.ofSeconds(10));
                                            bConfig.setFirstBackoff(Duration.ofSeconds(1));
                                            bConfig.setMaxBackoff(Duration.ofSeconds(10));
                                            bConfig.setFactor(2);

                                            f.filter(myGatewayFilter, 0)
                                                    .filter(myRewritePathGatewayFilterFactory.apply(new MyRewritePathGatewayFilterFactory.Config("get")))
                                                    .filter(myFilterFactory.apply(new MyGatewayFilterFactory.Config()))
                                                    .filter(
                                                            new RetryGatewayFilterFactory().apply(
                                                                    new RetryGatewayFilterFactory.RetryConfig()
                                                                            .setRetries(3)
                                                                            .setBackoff(bConfig)
                                                            ))
                                                    .addRequestHeader("first-request", "first-request-header")
                                                    .addResponseHeader("first-response", "first-response-header");
                                            return f;
                                        }
                                )

                        .uri("http://127.0.0.1:3000"))
                .route("post_route", r -> r.path("/post")
                        //Add predicate factory
                        .and()
                        .predicate(myRoutePredicateFactory.apply(new MyRoutePredicateFactory.Config("post")))
                        //Pre-post filters
                        .filters(f -> f.filter(myGatewayFilter, 0)
                                .addRequestHeader("first-request", "first-request-header")
                                .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://httpbin.org"))
                .route("get_route-data-para", r -> r.path("/posts/**")
                        //Add predicate factory
                        .and()
                        .predicate(myRoutePredicateFactory.apply(new MyRoutePredicateFactory.Config("get")))
                        //Pre-post filters
                        .filters
                                (f -> {
                                            RetryGatewayFilterFactory.BackoffConfig bConfig = new RetryGatewayFilterFactory.BackoffConfig();
                                            bConfig.setMaxBackoff(Duration.ofSeconds(10));
                                            bConfig.setFirstBackoff(Duration.ofSeconds(1));
                                            bConfig.setMaxBackoff(Duration.ofSeconds(10));
                                            bConfig.setFactor(2);

                                            f.filter(myGatewayFilter, 0)
                                                    //.filter(postsGatewayFilterFactory.apply(new PostsGatewayFilterFactory.Config()))
                                                    .filter(myFilterFactory.apply(new MyGatewayFilterFactory.Config()))
                                                    .filter(
                                                            new RetryGatewayFilterFactory().apply(
                                                                    new RetryGatewayFilterFactory.RetryConfig()
                                                                            .setRetries(3)
                                                                            .setBackoff(bConfig)
                                                            ))
                                                    .addRequestHeader("first-request", "first-request-header")
                                                    .addResponseHeader("first-response", "first-response-header");
                                            return f;
                                        }
                                )

                        .uri("http://127.0.0.1:3000/posts"))
                .build();
    }
}
