package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.Random;
import java.util.function.DoubleSupplier;

/**
 * @author chentong
 */
public class DoubleSupplierTest {
    private static final Random RANDOM = new Random();

    /**
     * one possible output is :
     *
     * 0.09430288390884012
     * 0.6812912583035311
     * 0.7761353368026288
     * 0.23783941009062637
     * 0.8243553763515822
     */
    @Test
    public void test_doubleSupplier_success() {
        DoubleSupplier supplier = RANDOM::nextDouble;
        for (int i = 0; i < 5; i++) {
            System.out.println(supplier.getAsDouble());
        }
    }
}
