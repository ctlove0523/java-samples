package io.github.ctlove0523.javasamples.bus;

/**
 * @author chentong
 */
public interface Handler {

	/**
	 * 处理EventBus推送的事件
	 * @param event {@link Event}
	 */
	void handle(Event event);
}
