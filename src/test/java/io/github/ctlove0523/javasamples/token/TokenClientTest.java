package io.github.ctlove0523.javasamples.token;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class TokenClientTest {

	@Test
	public void test() {
		IdentityVerifier identityVerifier = identity -> true;

		String key = UUID.randomUUID().toString();
		SignKeyPairProvider signKeyPairProvider = new SignKeyPairProvider() {
			@Override
			public SignKeyPair getSignKeyPair() {
				SignKeyPair signKeyPair = new SignKeyPair();
				signKeyPair.setCurrentKey(key);
				return signKeyPair;
			}

			@Override
			public void registerChangeHandler(SignKeyChangeHandler handler) {

			}
		};

		TokenClient tokenClient = new JwtTokenClient(signKeyPairProvider, identityVerifier);

		Identity identity = () -> "123";
		String token = tokenClient.getToken(identity);
		Assert.assertNotNull(token);

		Assert.assertTrue(tokenClient.validToken(token));
	}
}
