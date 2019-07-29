package io.github.ctlove0523.javasamples.functions;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author chentong
 */
public class BiFunctionTest {
    @Test
    public void test_biFunction_success() {
        BiFunction<String, Integer, String> biFunction = (s, integer) -> {
            Objects.requireNonNull(s);
            if (integer < s.length()) {
                return s;
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < integer / s.length(); i++) {
                    sb.append(s);
                    sb.append("=");
                }
                sb.append(s.subSequence(0, integer % s.length()));
                return sb.toString();
            }
        };

        Assert.assertEquals("hello", biFunction.apply("hello", 4));
        Assert.assertEquals("hello=hello=hell",biFunction.apply("hello",14));
    }

}
