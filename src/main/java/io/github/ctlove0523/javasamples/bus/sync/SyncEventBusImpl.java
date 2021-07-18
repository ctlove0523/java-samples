package io.github.ctlove0523.javasamples.bus.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import io.github.ctlove0523.javasamples.bus.Event;
import io.github.ctlove0523.javasamples.bus.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chentong
 */
public class SyncEventBusImpl implements SyncEventBus {
	private static final Logger log = LoggerFactory.getLogger(SyncEventBusImpl.class);

	private Map<String, List<Handler>> handlerMap = new ConcurrentHashMap<>();

	@Override
	public void registerHandler(String subject, Handler handler) {
		Objects.requireNonNull(subject, "subject");
		Objects.requireNonNull(handler, "handler");

		List<Handler> handlers = handlerMap.getOrDefault(subject, new ArrayList<>());
		handlers.add(handler);

		handlerMap.put(subject, handlers);
	}

	@Override
	public void post(Event event) {
		Objects.requireNonNull(event, "event");

		String subject = event.getSubject();

		List<Handler> handlers = handlerMap.getOrDefault(subject,new ArrayList<>());

		if (handlers.isEmpty()) {
			log.warn("no handler for {} subject", subject);
			return;
		}

		for (Handler handler : handlers) {
			try {
				handler.handle(event);
			}
			catch (Throwable t) {
				log.warn("call handler {} to handle event {} failed", handler.getClass(), event);
			}
		}
	}
}
