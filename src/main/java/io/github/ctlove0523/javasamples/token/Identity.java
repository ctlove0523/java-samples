package io.github.ctlove0523.javasamples.token;

/**
 * 用户或者组件的身份
 * @author chentong
 */
public interface Identity {

	/**
	 * 身份的唯一标识，不同身份的id不同
	 * @return 身份ID
	 */
	String getId();
}
