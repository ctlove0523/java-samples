package io.github.ctlove0523.javasamples.token;

public interface TokenClient {

	String getToken(Identity identity);

	boolean validToken(String token);
}
