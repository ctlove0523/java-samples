package io.github.ctlove0523.javasamples.hkdf;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Hkdf {
    private static final String HMAC_SHA256 = "hmacsha256";
    private static final String HMAC_SHA1 = "hmacsha1";

    private final String alg;

    public Hkdf() {
        this(HMAC_SHA256);
    }

    public Hkdf(String alg) {
        this.alg = alg;
    }

    public byte[] extract(byte[] ikm, byte[] salt) {
        try {
            if (salt == null || salt.length == 0) {
                salt = new byte[getHashLen(alg)];
            }
            SecretKeySpec signingKey = new SecretKeySpec(salt, alg);
            Mac mac = Mac.getInstance(alg);
            mac.init(signingKey);
            return mac.doFinal(ikm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] expand(byte[] prk, byte[] info, int length) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(prk, alg);
            Mac mac = Mac.getInstance(alg);
            mac.init(signingKey);

            int hashLen = getHashLen(alg);
            int ceil = (int) Math.ceil((double) length / (double) hashLen);
            if (hashLen == 0 || ceil > 255) {
                return null;
            }

            byte[] rawResult = new byte[0];
            byte[] t = new byte[0];
            for (int i = 1; i <= ceil; i++) {
                ByteArrayOutputStream hashBytes = new ByteArrayOutputStream();
                hashBytes.write(t);
                hashBytes.write(info);
                hashBytes.write(i);
                t = mac.doFinal(hashBytes.toByteArray());

                ByteArrayOutputStream combineBytes = new ByteArrayOutputStream();
                combineBytes.write(rawResult);
                combineBytes.write(t);
                rawResult = combineBytes.toByteArray();
            }

            ByteArrayInputStream in = new ByteArrayInputStream(rawResult);
            byte[] okm = new byte[length];
            in.read(okm);
            return okm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getHashLen(String hmac_alg) {
        if (HMAC_SHA1.equals(hmac_alg)) {
            return 20;
        }
        return 32;
    }
}
