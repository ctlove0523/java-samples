package io.github.ctlove0523.javasamples.password;

public interface PasswordPolicy {

    /**
     * 检查密码是否满足规则
     *
     * @param password 密码
     * @return 符合返回true，否则返回false
     */
    boolean pass(char[] password);
}
