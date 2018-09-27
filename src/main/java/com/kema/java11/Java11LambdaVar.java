package com.kema.java11;

import java.util.stream.IntStream;

public class Java11LambdaVar {

    public static void main(String[] args){
        Java11LambdaVar jf = new Java11LambdaVar();

        jf.lambdaVar();
    }

    public void lambdaVar(){
        IntStream s = IntStream.iterate( 0, i-> i<100, i -> i+2);
        //s = IntStream.of(1,2,3);

        System.out.println("java 8 way:");
        s.forEach( i -> {
            System.out.println(i);
        });

        s = IntStream.iterate( 0, i-> i<100, i -> i+2);
        //s = IntStream.of(1,2,3);

        System.out.println("java 11 way:");
        s.forEach((var i) -> {
            System.out.println(i);
        });
    }

}
