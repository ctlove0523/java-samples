package io.github.ctlove0523.javasamples.bus.sync;

import io.github.ctlove0523.javasamples.bus.EventBus;
import io.github.ctlove0523.javasamples.bus.Handler;

/**
 * 同步EventBus，推送事件的线程会等待所有{@link Handler}处理完成后返回
 *
 * @author chentong
 */
public interface SyncEventBus extends EventBus {

	/**
	 * 构建同步EventBus
	 * @return {@link SyncEventBus}
	 */
	static SyncEventBus build() {
		return new SyncEventBusImpl();
	}

	/**
	 * 注册处理器
	 * @param subject 事件对应的主题
	 * @param handler 事件处理器{@link Handler}
	 */
	void registerHandler(String subject, Handler handler);

}
