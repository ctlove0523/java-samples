package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.Random;
import java.util.function.IntSupplier;

public class IntSupplierExample {
    private static final Random RANDOM = new Random();

    /**
     * one possible output is：
     * 1866941818
     * -27661771
     * -99006351
     * 212458684
     * 216217939
     */
    @Test
    public void test_intSupplier_success() {
        IntSupplier intSupplier = RANDOM::nextInt;
        for (int i = 0; i < 5; i++) {
            System.out.println(intSupplier.getAsInt());
        }
    }
}
