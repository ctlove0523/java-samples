package io.github.ctlove0523.javasamples.functions;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * @author chentong
 */
public class FunctionTest {

    @Test
    public void test_function_success() {
        Function<String, Integer> function = String::length;
        Assert.assertEquals(5, (int) function.apply("Hello"));

        Function<Integer, Integer> secondFunc = integer -> 2 * integer;
        Assert.assertEquals(10, (int) secondFunc.compose(function).apply("Hello"));

        Assert.assertEquals(10, (int) function.andThen(secondFunc).apply("Hello"));

        Function<Integer, Integer> identityFunc = Function.identity();
        Assert.assertEquals(1, (int) identityFunc.apply(1));

    }
}
