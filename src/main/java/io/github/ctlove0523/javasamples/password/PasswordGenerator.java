package io.github.ctlove0523.javasamples.password;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface PasswordGenerator {

    /**
     * 创建{@link PasswordGenerator} 构建器
     *
     * @return {@link Builder}
     */
    static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 生成长度为length的密码
     *
     * @param length 密码长度，最小值为8
     * @return 符合条件的密码
     */
    String generate(int length);

    class Builder {
        private SecureRandom secureRandom;
        private char[] charset;
        private List<PasswordPolicy> passwordPolicies = new ArrayList<>();

        private Builder() {

        }

        public Builder withSecureRandom(SecureRandom secureRandom) {
            Objects.requireNonNull(secureRandom, "secureRandom");
            this.secureRandom = secureRandom;
            return this;
        }

        public Builder withCharset(char[] charset) {
            Objects.requireNonNull(charset, "charset");
            this.charset = charset;
            return this;
        }

        public Builder withCharset(String str) {
            Objects.requireNonNull(str, "str");
            this.charset = str.toCharArray();
            return this;
        }

        public Builder withPasswordPolicy(PasswordPolicy passwordPolicy) {
            Objects.requireNonNull(passwordPolicy, "passwordPolicy");
            this.passwordPolicies.add(passwordPolicy);
            return this;
        }

        public Builder withPasswordPolicy(List<PasswordPolicy> passwordPolicies) {
            Objects.requireNonNull(passwordPolicies, "passwordPolicies");
            this.passwordPolicies.addAll(passwordPolicies);
            return this;
        }

        public PasswordGenerator build() {
            return new DefaultPasswordGenerator(this);
        }

        public SecureRandom getSecureRandom() {
            return secureRandom;
        }

        public char[] getCharset() {
            return charset;
        }

        public List<PasswordPolicy> getPasswordPolicies() {
            return passwordPolicies;
        }
    }
}
