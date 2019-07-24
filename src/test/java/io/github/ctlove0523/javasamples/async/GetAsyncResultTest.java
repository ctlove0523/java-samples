package io.github.ctlove0523.javasamples.async;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetAddress;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: GetAsyncResultTest
 *
 * @author: chentong
 * Date:     2019/7/22 22:58
 */
public class GetAsyncResultTest {


    @Test
    public void test_getAsyncResult() {
        GetAsyncResultFornOtherApp app = new GetAsyncResultFornOtherApp();
        String result = app.getRemoteResponse();
        System.out.println(result);
    }

    @BeforeClass
    private void startServer() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("Hello Async Method"));
        InetAddress address = InetAddress.getByName("localhost");
        server.start(address,8080);
    }
}
