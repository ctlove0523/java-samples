package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.Random;
import java.util.function.BooleanSupplier;

public class BooleanSupplierExample {
    private static final Random RANDOM = new Random();

    /**
     * one possible output is:
     * false
     * false
     * true
     * true
     * true
     */
    @Test
    public void test_booleanSupplier_success() {
        BooleanSupplier supplier = RANDOM::nextBoolean;
        for (int i = 0; i < 5; i++) {
            System.out.println(supplier.getAsBoolean());
        }
    }
}
