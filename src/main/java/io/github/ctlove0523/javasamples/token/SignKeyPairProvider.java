package io.github.ctlove0523.javasamples.token;

public interface SignKeyPairProvider {

	SignKeyPair getSignKeyPair();

	void registerChangeHandler(SignKeyChangeHandler handler);
}
