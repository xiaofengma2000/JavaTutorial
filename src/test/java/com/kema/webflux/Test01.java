package com.kema.webflux;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test01 {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testPing() throws Exception{
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/ping")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Ping");
    }

    @Test
    public void testNedefQuery() throws Exception{
        NEDefinition result = webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/NEDef/ne01")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(NEDefinition.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals("ne01", result.getName());
    }

}
