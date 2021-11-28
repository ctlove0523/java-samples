package io.github.ctlove0523.javasamples.token;

public interface TokenClient {

	String getToken();

	boolean validToken(String token);
}
