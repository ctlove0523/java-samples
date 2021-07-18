package io.github.ctlove0523.javasamples.bus;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.github.ctlove0523.javasamples.bus.async.AsyncEventBus;
import org.junit.Assert;
import org.junit.Test;

public class AsyncEventBusTest {

	@Test
	public void test_asyncEventBus() throws Exception {
		EventHandler handler = new EventHandler();
		AsyncEventBus asyncEventBus = AsyncEventBus.build();
		asyncEventBus.registerHandler("TEST", handler);
		asyncEventBus.registerHandler("TEST", handler, Executors.newSingleThreadExecutor());

		for (int i = 0; i < 4; i++) {
			Event event = new Event() {
				@Override
				public String getSubject() {
					return "TEST";
				}

				@Override
				public Object getContent() {
					return "message";
				}
			};

			asyncEventBus.post(event);
		}

		TimeUnit.SECONDS.sleep(1L);

		Assert.assertEquals(8, handler.getMessages().size());
	}
}
