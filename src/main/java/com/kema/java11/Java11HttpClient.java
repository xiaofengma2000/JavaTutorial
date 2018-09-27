package com.kema.java11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Java11HttpClient {
    
    public static void main(String[] args){
        HttpClient hc = HttpClient.newBuilder().build();
        
        try{
            HttpRequest req = HttpRequest.newBuilder(URI.create("http://www.google.com")).build();
            final HttpResponse<String> res = hc.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("Blocking send : ");
            System.out.println(res.body());

            final CompletableFuture<Void> future = hc.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenApply(rs -> rs.body()).thenAccept(body -> {
                System.out.println("Async send : ");
                System.out.println(body);
            });
            future.get();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
