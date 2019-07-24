package io.github.ctlove0523.javasamples.async;


import okhttp3.Request;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: GetAsyncResultFornOtherApp
 * <p>
 * This code simulate A ------>B ------>C
 * async    async
 * the result return from C to B then to A
 *
 * @author: chentong
 * Date:     2019/7/22 22:33
 */
public class GetAsyncResultFornOtherApp {
    public static void main(String[] args) throws Exception {
    }

    public String getRemoteResponse() {
        Request request = new Request.Builder()
                .url("http://localhost:8080/v1.0/api/token")
                .build();
        AsyncResult<String> asyncResult = new AsyncResult<>();
        ApplicationB.INSTANCE.invoke(request, asyncResult);
        return asyncResult.getResult();
    }
}
