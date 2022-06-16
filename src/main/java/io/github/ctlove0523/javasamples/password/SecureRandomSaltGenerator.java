package io.github.ctlove0523.javasamples.password;

import java.security.SecureRandom;

public class SecureRandomSaltGenerator implements SaltGenerator {
    private static final int DEFAULT_SALT_LENGTH = 16;

    private final int saltLength;
    private final SecureRandom secureRandom;

    public SecureRandomSaltGenerator() {
        this(DEFAULT_SALT_LENGTH);
    }

    public SecureRandomSaltGenerator(int saltLength) {
        this.saltLength = Math.max(saltLength, DEFAULT_SALT_LENGTH);
        this.secureRandom = new SecureRandom();
    }

    @Override
    public byte[] generateSalt() {
        return generateSalt(saltLength);
    }

    @Override
    public byte[] generateSalt(int length) {
        // salt的长度不能低于默认值
        int finalSaltLength = Math.max(length, DEFAULT_SALT_LENGTH);

        byte[] salt = new byte[finalSaltLength];
        secureRandom.nextBytes(salt);

        return salt;
    }
}
