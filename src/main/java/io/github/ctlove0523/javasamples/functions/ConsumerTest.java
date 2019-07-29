package io.github.ctlove0523.javasamples.functions;

import org.junit.Test;

import java.util.function.Consumer;

/**
 * @author chentong
 */
public class ConsumerTest {
    /**
     * the output will be:
     * Hello Consumer
     * Hello Consumer	Hello Consumer
     * Hello Consumer
     * Hello Consumer	Hello Consumer
     * Hello Consumer	Hello Consumer
     * Hello Consumer
     */
    @Test
    public void test_consumerAccept_success() {
        Consumer<String> echoConsumer = System.out::println;
        echoConsumer.accept("Hello Consumer");
        Consumer<String> repConsumer = s -> System.out.println(s + "\t" + s);
        repConsumer.accept("Hello Consumer");
        Consumer<String> composeConsumer = echoConsumer.andThen(repConsumer);
        composeConsumer.accept("Hello Consumer");
        Consumer<String> revComposeConsumer = repConsumer.andThen(echoConsumer);
        revComposeConsumer.accept("Hello Consumer");

    }
}
