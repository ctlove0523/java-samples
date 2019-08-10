package io.github.ctlove0523.javasamples.concurrent;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author chentong
 */
public class CompletableFutureAndTest {

    /**
     * 下面的程序将会打印如下内容：
     * first completable future finished
     * second completable future finished
     * i can run now
     */
    @Test
    public void test_runAfterBoth_success() {
        CompletableFuture<Integer> firstCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("first completable future finished");
            return 1;
        });
        CompletableFuture<Integer> secondCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("second completable future finished");
            return 2;
        });
        firstCompletableFuture.runAfterBoth(secondCompletableFuture, new Runnable() {
            @Override
            public void run() {
                System.out.println("i can run now");
            }
        });
    }
}
