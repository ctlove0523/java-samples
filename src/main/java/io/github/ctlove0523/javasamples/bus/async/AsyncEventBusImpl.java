package io.github.ctlove0523.javasamples.bus.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.github.ctlove0523.javasamples.bus.Event;
import io.github.ctlove0523.javasamples.bus.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chentong
 */
public class AsyncEventBusImpl implements AsyncEventBus {
	private static final Logger log = LoggerFactory.getLogger(AsyncEventBusImpl.class);
	private Executor mainExecutor;

	private Map<String, List<HandlerWrapper>> handlers = new HashMap<>();

	AsyncEventBusImpl() {
		this.mainExecutor = new ThreadPoolExecutor(1, 1,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), Executors
				.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	AsyncEventBusImpl(Executor mainExecutor) {
		this.mainExecutor = mainExecutor;
	}

	@Override
	public void post(Event event) {
		Objects.requireNonNull(event, "event");

		String subject = event.getSubject();

		List<HandlerWrapper> handlerWrappers = handlers.getOrDefault(subject, new ArrayList<>());
		if (handlerWrappers.isEmpty()) {
			log.warn("no async handler for subject {}", subject);
			return;
		}

		for (HandlerWrapper handler : handlerWrappers) {
			if (handler.executor != null) {
				handler.executor.execute(() -> handler.handler.handle(event));
			}
			else {
				if (mainExecutor != null) {
					mainExecutor.execute(() -> handler.handler.handle(event));
				}
				else {
					handler.handler.handle(event);
				}
			}
		}
	}

	@Override
	public void registerHandler(String subject, Handler handler, Executor executor) {
		Objects.requireNonNull(subject, "subject");
		Objects.requireNonNull(handler, "handler");
		Objects.requireNonNull(executor, "executor");

		List<HandlerWrapper> handlerWrappers = handlers.getOrDefault(subject, new ArrayList<>());
		HandlerWrapper newHandler = new HandlerWrapper(executor, handler);

		handlerWrappers.add(newHandler);

		handlers.put(subject, handlerWrappers);
	}

	@Override
	public void registerHandler(String subject, Handler handler) {
		Objects.requireNonNull(subject, "subject");
		Objects.requireNonNull(handler, "handler");

		List<HandlerWrapper> handlerWrappers = handlers.getOrDefault(subject, new ArrayList<>());
		HandlerWrapper newHandler = new HandlerWrapper(mainExecutor, handler);

		handlerWrappers.add(newHandler);

		handlers.put(subject, handlerWrappers);
	}

	private static class HandlerWrapper {
		private final Executor executor;
		private final Handler handler;

		HandlerWrapper(Executor executor, Handler handler) {
			this.executor = executor;
			this.handler = handler;
		}
	}
}
