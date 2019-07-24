package io.github.ctlove0523.javasamples.async;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: ApplicationB
 *
 * @author: chentong
 * Date:     2019/7/22 22:38
 */
public enum  ApplicationB {
    /**
     * 单例
     */
    INSTANCE;

    private OkHttpClient client = new OkHttpClient.Builder()
            .build();


    public void invoke(Request request,AsyncResult asyncResult) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                asyncResult.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                asyncResult.onResponse(response.body().string());
            }
        });
    }

}
