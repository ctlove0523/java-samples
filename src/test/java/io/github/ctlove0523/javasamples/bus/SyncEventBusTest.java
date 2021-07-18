package io.github.ctlove0523.javasamples.bus;

import java.util.ArrayList;
import java.util.List;

import io.github.ctlove0523.javasamples.bus.sync.SyncEventBus;
import org.junit.Assert;
import org.junit.Test;

public class SyncEventBusTest {
	@Test
	public void test_syncEventBus() {
		EventHandler handler = new EventHandler();
		SyncEventBus syncEventBus = SyncEventBus.build();
		syncEventBus.registerHandler("TEST", handler);

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

			syncEventBus.post(event);
		}

		Assert.assertEquals(4, handler.getMessages().size());
	}
}

