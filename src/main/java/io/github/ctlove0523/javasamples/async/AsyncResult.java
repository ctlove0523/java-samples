package io.github.ctlove0523.javasamples.async;

import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: AsyncResult
 *
 * @author: chentong
 * Date:     2019/7/22 22:40
 */
public class AsyncResult<T> {
    private CompletableFuture<T> future = new CompletableFuture<>();

    public T getResult() {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    void onFailure(IOException e) {
        future.completeExceptionally(e);
    }

    void onResponse(T response) throws IOException {
        future.complete(response);
    }
}
