package io.github.ctlove0523.javasamples.password;

import java.security.SecureRandom;
import java.util.List;

public class DefaultPasswordGenerator implements PasswordGenerator {
    private static final char[] DEFAULT_CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*".toCharArray();

    private final SecureRandom secureRandom;
    private final char[] charset;
    private final List<PasswordPolicy> passwordPolicies;

    public DefaultPasswordGenerator(Builder builder) {
        if (builder.getSecureRandom() != null) {
            this.secureRandom = builder.getSecureRandom();
        } else {
            this.secureRandom = new SecureRandom();
        }

        if (builder.getCharset() != null) {
            this.charset = builder.getCharset();
        } else {
            this.charset = DEFAULT_CHARSET;
        }

        this.passwordPolicies = builder.getPasswordPolicies();

    }

    @Override
    public String generate(int length) {
        if (length < 8) {
            length = 8;
        }
        char[] password = new char[length];
        beginLoop:
        for (; ; ) {
            for (int i = 0; i < length; i++) {
                int index = Math.abs(secureRandom.nextInt()) % charset.length;
                password[i] = charset[index];
            }

            for (PasswordPolicy policy : passwordPolicies) {
                if (!policy.pass(password)) {
                    continue beginLoop;
                }
            }
            break;
        }
        return new String(password);
    }
}
