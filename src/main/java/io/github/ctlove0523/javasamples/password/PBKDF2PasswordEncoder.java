package io.github.ctlove0523.javasamples.password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PBKDF2PasswordEncoder {
    private final String algorithm;
    private final int iterations;
    private final SaltGenerator saltGenerator;
    private final int passwordLength;

    public PBKDF2PasswordEncoder(String algorithm, int iterations, SaltGenerator saltGenerator, int passwordLength) {
        this.algorithm = algorithm;
        this.iterations = iterations;
        this.saltGenerator = saltGenerator;
        this.passwordLength = passwordLength;
    }

    /**
     * alg:salt:iterations:pwd
     */
    public String encode(CharSequence rawPassword) {
        byte[] salt = saltGenerator.generateSalt();
        return encode(rawPassword, salt, this.iterations, this.passwordLength, this.algorithm);
    }

    private String encode(CharSequence rawPassword, byte[] salt, int iterations, int passwordLength, String algorithm) {
        try {
            PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(), salt, iterations, passwordLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            byte[] pwdHash = skf.generateSecret(spec).getEncoded();

            // 组装各个部分
            List<String> parts = new ArrayList<>(4);
            parts.add(algorithm);
            parts.add(Base64.getEncoder().encodeToString(salt));
            parts.add(Integer.toString(iterations));
            parts.add(Base64.getEncoder().encodeToString(pwdHash));
            return String.join(":", parts);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException("can't create hash");
        }

    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String alg = getAlgorithm(encodedPassword);
        byte[] salt = getSalt(encodedPassword);
        int iterations = getIterations(encodedPassword);
        int passwordLength = getPasswordLength(encodedPassword);

        String expected = encode(rawPassword, salt, iterations, passwordLength, alg);

        return expected.equals(encodedPassword);
    }

    private String getAlgorithm(String encodedPassword) {
        return encodedPassword.split(":")[0];
    }

    private byte[] getSalt(String encodedPassword) {
        return Base64.getDecoder().decode(encodedPassword.split(":")[1]);
    }

    private int getIterations(String encodedPassword) {
        return Integer.parseInt(encodedPassword.split(":")[2]);
    }

    private int getPasswordLength(String encodedPassword) {
        return Base64.getDecoder().decode(encodedPassword.split(":")[3]).length *8;
    }
}
