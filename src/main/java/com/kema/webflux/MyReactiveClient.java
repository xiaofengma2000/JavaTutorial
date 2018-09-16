package com.kema.webflux;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ForkJoinPool;

public class MyReactiveClient {

    static WebClient client = WebClient.create("http://127.0.0.1:8090");

    public static void main(String[] args){

        for(int i=0;i<100;i++){
            if(i % 2 == 0){
                ForkJoinPool.commonPool().execute(() -> {
                    ping();
                });
            }
            else
            {
                ForkJoinPool.commonPool().execute(() -> {
                    getNeDef();
                });
            }
        }
    }

    private static void getNeDef() {
        Mono<NEDefinition> getNeRes = client.get()
                .uri("/NEDef/{name}","ne01")
                .retrieve().bodyToMono(NEDefinition.class);
        System.out.println(getNeRes.block().getName());
    }

    private static void ping() {
        final Mono<String> pingRes = client.get()
                .uri("/ping")
                .retrieve().bodyToMono(String.class);
        System.out.println(pingRes.block());
    }
}
