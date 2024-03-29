package io.github.ctlove0523.javasamples.hkdf;

import org.junit.Assert;
import org.junit.Test;

/**
 * 所有的测试用例来自 rfc5969
 */
public class HkdfTest {
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (bytes != null && bytes.length > 0) {
            // 增加十六进制字符串的标记
            sb.append(0);
            sb.append('x');

            for (byte b : bytes) {
                int v = b & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    sb.append(0);
                }

                sb.append(hv);
            }
        }
        return sb.toString();
    }

    public static byte[] hexToBytes(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }

        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }

        byte[] binary = new byte[hex.length() / 2];
        if (hex.length() > 0) {
            for (int i = 0; i < binary.length; i++) {
                binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
            }

        }
        return binary;
    }


    /**
     * Hash = SHA-256
     * IKM = 0x0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b (22 octets)
     * salt = 0x000102030405060708090a0b0c (13 octets)
     * info = 0xf0f1f2f3f4f5f6f7f8f9 (10 octets)
     * L = 42
     * PRK = 0x077709362c2e32df0ddc3f0dc47bba6390b6c73bb50f9c3122ec844ad7c2b3e5 (32 octets)
     * OKM = 0x3cb25f25faacd57a90434f64d0362f2a2d2d0a90cf1a5a4c5db02d56ecc4c5bf34007208d5b887185865 (42 octets)
     */
    @Test
    public void testCase1() {
        String ikm = "0x0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
        String salt = "0x000102030405060708090a0b0c";

        Hkdf hkdf = new Hkdf("hmacsha256");
        byte[] prk = hkdf.extract(hexToBytes(ikm), hexToBytes(salt));
        String prkString = bytesToHex(prk);

        String expectedPrk = "0x077709362c2e32df0ddc3f0dc47bba6390b6c73bb50f9c3122ec844ad7c2b3e5";
        Assert.assertEquals(expectedPrk, prkString);

        String info = "0xf0f1f2f3f4f5f6f7f8f9";
        byte[] okm = hkdf.expand(prk, hexToBytes(info), 42);

        String expectedOkm = "0x3cb25f25faacd57a90434f64d0362f2a2d2d0a90cf1a5a4c5db02d56ecc4c5bf34007208d5b887185865";
        Assert.assertEquals(expectedOkm, bytesToHex(okm));
    }

    /**
     * Hash = SHA-256
     * IKM =  0x000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f (80 octets)
     * salt = 0x606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf (80 octets)
     * info = 0xb0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff (80 octets)
     * L = 82
     * PRK = 0x06a6b88c5853361a06104c9ceb35b45cef760014904671014a193f40c15fc244 (32 octets)
     * OKM = 0xb11e398dc80327a1c8e7f78c596a49344f012eda2d4efad8a050cc4c19afa97c59045a99cac7827271cb41c65e590e09da3275600c2f09b8367793a9aca3db71cc30c58179ec3e87c14c01d5c1f3434f1d87 (82 octets)
     */

    @Test
    public void testCase2() {
        String ikm = "0x000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f";
        String salt = "0x606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf";

        Hkdf hkdf = new Hkdf("hmacsha256");
        byte[] prk = hkdf.extract(hexToBytes(ikm), hexToBytes(salt));
        String prkString = bytesToHex(prk);

        String expectedPrk = "0x06a6b88c5853361a06104c9ceb35b45cef760014904671014a193f40c15fc244";
        Assert.assertEquals(expectedPrk, prkString);

        String info = "0xb0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff";
        byte[] okm = hkdf.expand(prk, hexToBytes(info), 82);

        String expectedOkm = "0xb11e398dc80327a1c8e7f78c596a49344f012eda2d4efad8a050cc4c19afa97c59045a99cac7827271cb41c65e590e09da3275600c2f09b8367793a9aca3db71cc30c58179ec3e87c14c01d5c1f3434f1d87";
        Assert.assertEquals(expectedOkm, bytesToHex(okm));
    }

    @Test
    public void testHkdf() {
        String[] params = testInputs.split("\n");
        for (int i = 0; i < 7; i++) {
            String hashAlg = getHashAlg(params[i * 7]);
            String ikm = getValue(params[i * 7 + 1]);
            String salt = getSlat(params[i * 7 + 2]);
            String info = getValue(params[i * 7 + 3]);
            int length = getLen(params[i * 7 + 4]);
            String pkr = getValue(params[i * 7 + 5]);
            String okm = getValue(params[i * 7 + 6]);


            Hkdf hkdf = new Hkdf(hashAlg);
            byte[] pkrBytes = hkdf.extract(hexToBytes(ikm), hexToBytes(salt));
            String prkString = bytesToHex(pkrBytes);
            Assert.assertEquals(pkr, prkString);

            byte[] okmBytes = hkdf.expand(pkrBytes, hexToBytes(info), length);

            Assert.assertEquals(okm, bytesToHex(okmBytes));
        }
    }

    private String getHashAlg(String hashInput) {
        if (hashInput.contains("SHA-256")) {
            return "hmacsha256";
        }

        return "hmacsha1";
    }

    private String getValue(String line) {
        String[] segments = line.split("=");
        String rawIkm = segments[1].trim();
        return rawIkm.substring(0, rawIkm.indexOf("(")).trim();
    }

    private String getSlat(String slatInput) {
        if (slatInput.contains("not provided")) {
            return "";
        }

        return getValue(slatInput);
    }

    private int getLen(String input) {
        return Integer.parseInt(input.split("=")[1].trim());
    }

    private final String testInputs = "Hash = SHA-256\n" +
            "IKM = 0x0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b (22 octets)\n" +
            "salt = 0x000102030405060708090a0b0c (13 octets)\n" +
            "info = 0xf0f1f2f3f4f5f6f7f8f9 (10 octets)\n" +
            "L = 42\n" +
            "PRK = 0x077709362c2e32df0ddc3f0dc47bba6390b6c73bb50f9c3122ec844ad7c2b3e5 (32 octets)\n" +
            "OKM = 0x3cb25f25faacd57a90434f64d0362f2a2d2d0a90cf1a5a4c5db02d56ecc4c5bf34007208d5b887185865 (42 octets)\n" +
            "Hash = SHA-256\n" +
            "IKM = 0x000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f (80 octets)\n" +
            "salt = 0x606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf (80 octets)\n" +
            "info = 0xb0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff (80 octets)\n" +
            "L = 82\n" +
            "PRK = 0x06a6b88c5853361a06104c9ceb35b45cef760014904671014a193f40c15fc244 (32 octets)\n" +
            "OKM = 0xb11e398dc80327a1c8e7f78c596a49344f012eda2d4efad8a050cc4c19afa97c59045a99cac7827271cb41c65e590e09da3275600c2f09b8367793a9aca3db71cc30c58179ec3e87c14c01d5c1f3434f1d87 (82 octets)\n" +
            "Hash = SHA-256\n" +
            "IKM = 0x0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b (22 octets)\n" +
            "salt = (0 octets)\n" +
            "info = (0 octets)\n" +
            "L = 42\n" +
            "PRK = 0x19ef24a32c717b167f33a91d6f648bdf96596776afdb6377ac434c1c293ccb04 (32 octets)\n" +
            "OKM = 0x8da4e775a563c18f715f802a063c5a31b8a11f5c5ee1879ec3454e5f3c738d2d9d201395faa4b61a96c8 (42 octets)\n" +
            "Hash = SHA-1\n" +
            "IKM = 0x0b0b0b0b0b0b0b0b0b0b0b (11 octets)\n" +
            "salt = 0x000102030405060708090a0b0c (13 octets)\n" +
            "info = 0xf0f1f2f3f4f5f6f7f8f9 (10 octets)\n" +
            "L = 42\n" +
            "PRK = 0x9b6c18c432a7bf8f0e71c8eb88f4b30baa2ba243 (20 octets)\n" +
            "OKM = 0x085a01ea1b10f36933068b56efa5ad81a4f14b822f5b091568a9cdd4f155fda2c22e422478d305f3f896 (42 octets)\n" +
            "Hash = SHA-1\n" +
            "IKM = 0x000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f (80 octets)\n" +
            "salt = 0x606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf (80 octets)\n" +
            "info = 0xb0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff (80 octets)\n" +
            "L = 82\n" +
            "PRK = 0x8adae09a2a307059478d309b26c4115a224cfaf6 (20 octets)\n" +
            "OKM = 0x0bd770a74d1160f7c9f12cd5912a06ebff6adcae899d92191fe4305673ba2ffe8fa3f1a4e5ad79f3f334b3b202b2173c486ea37ce3d397ed034c7f9dfeb15c5e927336d0441f4c4300e2cff0d0900b52d3b4 (82 octets)\n" +
            "Hash = SHA-1\n" +
            "IKM = 0x0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b (22 octets)\n" +
            "salt = (0 octets)\n" +
            "info = (0 octets)\n" +
            "L = 42\n" +
            "PRK = 0xda8c8a73c7fa77288ec6f5e7c297786aa0d32d01 (20 octets)\n" +
            "OKM = 0x0ac1af7002b3d761d1e55298da9d0506b9ae52057220a306e07b6b87e8df21d0ea00033de03984d34918 (42 octets)\n" +
            "Hash = SHA-1\n" +
            "IKM = 0x0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c (22 octets)\n" +
            "salt = not provided (defaults to HashLen zero octets)\n" +
            "info = (0 octets)\n" +
            "L = 42\n" +
            "PRK = 0x2adccada18779e7c2077ad2eb19d3f3e731385dd (20 octets)\n" +
            "OKM = 0x2c91117204d745f3500d636a62f64f0ab3bae548aa53d423b0d1f27ebba6f5e5673a081d70cce7acfc48 (42 octets)";


}
