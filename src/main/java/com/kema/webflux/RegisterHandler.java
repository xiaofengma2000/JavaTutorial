package com.kema.webflux;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RegisterHandler {

    public Mono<ServerResponse> ping(ServerRequest request) {
        return getOkBody("Ping");
    }

    private Mono<ServerResponse> getOkBody(String msg) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject(msg));
    }

    public Mono<ServerResponse> getNeDef(ServerRequest req){
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        String name = req.pathVariable("name");
        Mono<NEDefinition> m = Mono.just(new NEDefinition(name));

        return m.flatMap(neDefinition -> ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromObject(neDefinition)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> register(ServerRequest req){
        Mono<NEDefinition> neMono = req.bodyToMono(NEDefinition.class);

        Mono<Void> result = neMono.doOnNext(nedef -> {
            //save the ne definition
            System.out.println("Ne <" + nedef.getName() +
                    "> is registered!");

        }).thenEmpty(Mono.empty());
        return ServerResponse.ok().build(result);
    }


}
