package io.github.ctlove0523.javasamples.token;

import java.util.UUID;

import org.junit.Test;

public class TokenClientTest {

	@Test
	public void test() {
		IdentityProvider identityProvider = new IdentityProvider() {
			@Override
			public Identity getIdentity() {
				return new Identity() {
					@Override
					public String getId() {
						return "1";
					}
				};
			}

			@Override
			public boolean validIdentity(Identity identity) {
				return true;
			}
		};

		SignKeyPairProvider signKeyPairProvider = new SignKeyPairProvider() {
			@Override
			public SignKeyPair getSignKeyPair() {
				SignKeyPair signKeyPair = new SignKeyPair();
				signKeyPair.setCurrentKey(UUID.randomUUID().toString());
				return signKeyPair;
			}

			@Override
			public void registerChangeHandler(SignKeyChangeHandler handler) {

			}
		};

		TokenClient tokenClient = new JwtTokenClient(signKeyPairProvider, identityProvider);

		String token = tokenClient.getToken();
		System.out.println(token);
	}
}
