package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class SupplierExample {
    private static final Random RANDOM = new Random();

    /**
     * one possible output is
     * -1534183527
     * 4c19ca2e-3f73-4f34-a049-33f03b637fe2
     */
    @Test
    public void test_supplier_success() {
        Supplier<Integer> supplier = RANDOM::nextInt;
        System.out.println(supplier.get());
        Supplier<String> stringSupplier = () -> UUID.randomUUID().toString();
        System.out.println(stringSupplier.get());
    }
}
