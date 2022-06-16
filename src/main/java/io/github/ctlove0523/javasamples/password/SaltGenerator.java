package io.github.ctlove0523.javasamples.password;

public interface SaltGenerator {

    byte[] generateSalt();

    byte[] generateSalt(int length);
}
