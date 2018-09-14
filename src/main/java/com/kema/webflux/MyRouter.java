package com.kema.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class MyRouter {

    @Bean
    public RouterFunction<ServerResponse> route(RegisterHandler registerHandler){
        return RouterFunctions.route(RequestPredicates.GET("/ping"),
                                    registerHandler::ping)
                .andRoute(RequestPredicates.POST("/NEDef"),
                        registerHandler::register)
                .andRoute(RequestPredicates.GET("/NEDef/{name}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        registerHandler::getNeDef);
    }

}
