package io.github.ctlove0523.javasamples.bus;

/**
 * 事件总线
 * @author chentong
 */
public interface EventBus {
	/**
	 * 推送事件到处理器
	 * @param event {@link Event}
	 */
	void post(Event event);
}
