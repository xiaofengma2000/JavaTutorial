package com.kema.java11;

import java.util.Arrays;
import java.util.List;

public class Java11ToArray {

    public static void main(String[] args){

        var list = Arrays.asList("Item 1", "item 2", "item 3");
        System.out.println(list.getClass());

        System.out.println(Arrays.toString(list.toArray(new String[0])));

        System.out.println(Arrays.toString(list.toArray(String[]::new)));

    }

}
