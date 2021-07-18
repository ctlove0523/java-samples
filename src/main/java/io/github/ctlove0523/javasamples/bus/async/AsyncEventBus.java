package io.github.ctlove0523.javasamples.bus.async;

import java.util.concurrent.Executor;

import io.github.ctlove0523.javasamples.bus.EventBus;
import io.github.ctlove0523.javasamples.bus.Handler;

/**
 * @author chentong
 */
public interface AsyncEventBus extends EventBus {

	/**
	 * 创建一个异步事件总线，总线默认使用executor作为异步执行线程
	 *
	 * @param executor 默认执行器
	 * @return {@link AsyncEventBus}
	 */
	static AsyncEventBus build(Executor executor) {
		return new AsyncEventBusImpl(executor);
	}

	/**
	 * 创建一个异步事件总线，使用实现默认的线程异步执行
	 *
	 * @return {@link AsyncEventBus}
	 */
	static AsyncEventBus build() {
		return new AsyncEventBusImpl();
	}

	/**
	 * 注册事件handler
	 *
	 * @param subject 订阅的主题
	 * @param handler 主题所有事件的处理器
	 * @param executor 异步执行器
	 */
	void registerHandler(String subject, Handler handler, Executor executor);

	/**
	 * 注册事件handler
	 *
	 * @param subject 订阅的主题
	 * @param handler 主题所有事件的处理器
	 */
	void registerHandler(String subject, Handler handler);
}
