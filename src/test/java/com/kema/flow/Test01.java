package com.kema.flow;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test01 {

    public static void sleep(long millis){
        try{
            Thread.sleep(millis);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFlow() throws Exception{

        IntStream s = IntStream.iterate(0, i -> i+2);
        //s = IntStream.of(1,2,3);
        MyPublisher<Integer> pub = new MyPublisher<>();
        MySubscriber<Integer> sub = new MySubscriber<>(i -> sleep(100));
        MySubscriber<Integer> sub2 = new MySubscriber<>(i -> sleep(200));
        pub.subscribe(sub);
        pub.subscribe(sub2);

        s.forEach( i -> {
            sleep(100);
            pub.submit(i);
        });
        Thread.sleep(50000);
    }
}
