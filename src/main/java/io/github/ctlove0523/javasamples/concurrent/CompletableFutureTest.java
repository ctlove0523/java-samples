package io.github.ctlove0523.javasamples.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * @author chentong
 */
public class CompletableFutureTest {
    @Test
    public void test_getResultMethod_success() {
        CompletableFuture<Integer> asyncResult = CompletableFuture.supplyAsync(() -> 1);
        try {
            Assert.assertEquals(1, asyncResult.get().intValue());
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }

        CompletableFuture<Integer> timeoutResult = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        try {
            timeoutResult.get(200, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Assert.assertNotNull(e);
            Assert.assertTrue(e instanceof TimeoutException);
        }
        Assert.assertEquals(2, timeoutResult.getNow(2).intValue());
        Assert.assertEquals(1, timeoutResult.join().intValue());
    }

    @Test
    public void test_staticMethod_success() {
        CompletableFuture.completedFuture(1)
                .whenComplete((integer, throwable) -> {
                    Assert.assertNotNull(integer);
                    Assert.assertEquals(integer.intValue(), 1);
                });

        AsyncTask task = new AsyncTask();
        CompletableFuture.runAsync(task).whenComplete((aVoid, throwable) -> {
        });
        Assert.assertTrue(task.getExecuteThread().startsWith("ForkJoinPool"));

        Executor executor = newSingleThreadExecutor();
        AsyncTask task1 = new AsyncTask();
        CompletableFuture.runAsync(task1, executor).whenComplete((aVoid, throwable) -> {
        });
        Assert.assertTrue(task1.getExecuteThread().startsWith("pool-1"));

        CompletableFuture.supplyAsync(() -> 23).whenComplete((integer, throwable) -> {
            Assert.assertNotNull(integer);
            Assert.assertEquals(23, integer.intValue());
        });

        CompletableFuture.supplyAsync(() -> 23, executor).whenComplete((integer, throwable) -> {
            Assert.assertNotNull(integer);
            Assert.assertEquals(23, integer.intValue());
        });

    }

    @Test
    public void testStatusFunctionSuccess() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello Completable Future";
        });
        Assert.assertTrue(completableFuture.cancel(true));
        Assert.assertTrue(completableFuture.isCancelled());
        Assert.assertTrue(completableFuture.isCompletedExceptionally());
    }

    @Test
    public void test_obtrudeValue_success() throws Exception {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello Completable Future");
        Assert.assertEquals("Hello Completable Future", completableFuture.get());
        completableFuture.obtrudeValue("obtrude value");
        Assert.assertEquals("obtrude value", completableFuture.get());
    }

    @Test
    public void test_obtrudeException_success() {
        CompletableFuture<String> obtrudeException = CompletableFuture.supplyAsync(() -> "Hello Completable Future");
        obtrudeException.obtrudeException(new IndexOutOfBoundsException());

        try {
            obtrudeException.get();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            Assert.assertTrue(cause instanceof IndexOutOfBoundsException);
        }
    }

    @Test
    public void test_getNumberOfDependents_success() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        CompletableFuture<Integer> f2 = f1.thenApply(integer -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2 * integer;
        });
        CompletableFuture<Integer> f3 = f1.thenApply(integer -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2 * integer;
        });
        Assert.assertEquals(2, f1.getNumberOfDependents());
        Assert.assertEquals(0, f2.getNumberOfDependents());
        Assert.assertEquals(0, f3.getNumberOfDependents());
    }

    private static class AsyncTask implements Runnable {
        private String executeThread;

        @Override
        public void run() {
            executeThread = Thread.currentThread().getName();
        }

        String getExecuteThread() {
            return executeThread;
        }
    }
}
