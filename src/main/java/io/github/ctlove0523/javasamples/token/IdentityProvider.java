package io.github.ctlove0523.javasamples.token;

public interface IdentityProvider {

	Identity getIdentity();

	boolean validIdentity(Identity identity);
}
