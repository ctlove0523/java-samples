package io.github.ctlove0523.javasamples.token;

import java.util.Objects;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Strings;
import io.github.ctlove0523.javasamples.JacksonUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenClient implements TokenClient {
	private SignKeyPairProvider keyPairProvider;
	private IdentityProvider identityProvider;

	private Cache<String, String> tokenCache = Caffeine.newBuilder()
			.build();

	private Cache<String, Boolean> tokenHistory = Caffeine.newBuilder()
			.build();

	JwtTokenClient(SignKeyPairProvider keyPairProvider, IdentityProvider identityProvider) {
		this.keyPairProvider = keyPairProvider;
		this.identityProvider = identityProvider;
	}

	@Override
	public String getToken() {
		Identity identity = identityProvider.getIdentity();
		if (Objects.isNull(identity)) {
			return "";
		}

		String id = identity.getId();
		String token = tokenCache.getIfPresent(id);
		if (Objects.nonNull(token)) {
			return token;
		}

		// create new token
		SignKeyPair signKeyPair = keyPairProvider.getSignKeyPair();
		if (Objects.isNull(signKeyPair) || Objects.isNull(signKeyPair.getCurrentKey())) {
			return "";
		}

		token = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, signKeyPair.getCurrentKey())
				.setPayload(JacksonUtil.object2Json(identity))
				.compact();
		tokenCache.put(id, token);

		return token;
	}

	@Override
	public boolean validToken(String token) {
		if (Strings.isNullOrEmpty(token)) {
			return false;
		}

		Boolean res = tokenHistory.getIfPresent(token);
		if (Objects.nonNull(res)) {
			return res;
		}

		SignKeyPair signKeyPair = keyPairProvider.getSignKeyPair();
		if (Objects.isNull(signKeyPair) || Objects.isNull(signKeyPair.getCurrentKey())) {
			return false;
		}

		String payload = Jwts.parser().setSigningKey(signKeyPair.getCurrentKey())
				.parseClaimsJws(token).getSignature();
		if (Strings.isNullOrEmpty(payload)) {
			return false;
		}

		Identity identity = JacksonUtil.json2Object(payload, Identity.class);
		if (identityProvider.validIdentity(identity)) {
			tokenHistory.put(token, true);
			return true;
		}
		else {
			tokenHistory.put(token, false);
			return false;
		}

	}
}
