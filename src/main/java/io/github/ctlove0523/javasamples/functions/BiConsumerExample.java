package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.function.BiConsumer;

public class BiConsumerExample {
    /**
     * the output will be:
     * 3
     * 1
     * 7
     * 1
     */
    @Test
    public void test_biConsumer_success() {
        BiConsumer<Integer, Integer> firstConsumer = (first, second) -> System.out.println(first + second);
        firstConsumer.accept(1, 2);
        BiConsumer<Integer, Integer> secondConsumer = (first, second) -> System.out.println(first - second);
        secondConsumer.accept(2, 1);
        BiConsumer<Integer, Integer> composeConsumer = firstConsumer.andThen(secondConsumer);
        composeConsumer.accept(4, 3);
    }
}
