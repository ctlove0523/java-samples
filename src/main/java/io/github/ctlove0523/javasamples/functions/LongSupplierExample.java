package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.Random;
import java.util.function.LongSupplier;

public class LongSupplierExample {
    private static final Random RANDOM = new Random();

    /**
     * one possible output is(L is add for read)：
     *
     * 4969693884054271300L
     * -5199117218757284523L
     * 2460417923481373431L
     * -1152410285908503009L
     * -4013803504032040502L
     */
    @Test
    public void  test_longSupplier_success() {
        LongSupplier longSupplier = RANDOM::nextLong;
        for (int i =0;i<5;i++) {
            System.out.println(longSupplier.getAsLong());
        }
    }
}
