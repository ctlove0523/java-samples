package io.github.ctlove0523.javasamples.stackoverflow;

import com.google.common.collect.Streams;

import java.util.Arrays;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: CacheService
 *
 * @author: chentong
 * Date:     2019/2/22 20:52
 */
public class CacheService {

    public static void main(String[] args) throws Exception {
    }

    public static void compareArrays(int[] arr1, int[] arr2) {
        Streams.zip(Arrays.stream(arr1).boxed(), Arrays.stream(arr2).boxed()
                , (integer, integer2) -> integer - integer2 == 0 ? "=" : integer - integer2 > 0 ? "+" : "-")
                .forEach(s -> System.out.println(s));
    }
}
