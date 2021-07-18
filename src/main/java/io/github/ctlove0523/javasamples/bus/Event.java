package io.github.ctlove0523.javasamples.bus;

/**
 * @author chentong
 */
public interface Event {
	/**
	 * 事件主题,一个事件的主题唯一
	 * @return 字符串格式的主题，比如addApp,deleteApp
	 */
	String getSubject();

	/**
	 * 事件的内容
	 * @return 事件的内容，事件传输的内容
	 */
	Object getContent();
}
