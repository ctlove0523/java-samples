package io.github.ctlove0523.javasamples.problems;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

/**
 * @author chentong
 */
public class StringMathTest {

    @Test
    public void test_plus_success() {
        StringMathTest stringMath = new StringMathTest();
        Assert.assertEquals("3", stringMath.plus("1", "2"));
        Assert.assertEquals("10", stringMath.plus("1", "9"));
        Assert.assertEquals("222222", stringMath.plus("222222", "0"));
        Assert.assertEquals("1111111111111111110", stringMath.plus("112233445566778899", "998877665544332211"));
    }

    @Test
    public void test_subtract_success() {
        StringMathTest stringMath = new StringMathTest();
        Assert.assertEquals("0", stringMath.subtract("1", "1"));
        Assert.assertEquals("-2", stringMath.subtract("0", "2"));
    }

    private String subtract(String a, String b) {
        boolean negative = false;
        String subtrahend = b;
        String minuend = a;
        if (needReverse(a, b)) {
            negative = true;
            minuend = b;
            subtrahend = a;
        }
        char[] minuendArray = new StringBuilder(minuend).reverse().toString().toCharArray();
        char[] subtrahendArray = new StringBuilder(subtrahend).reverse().toString().toCharArray();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i < minuendArray.length && i < subtrahendArray.length; i++) {
            int tmpa = minuendArray[i] - '0';
            int tmpb = subtrahendArray[i] - '0';
            if (tmpa >= tmpb) {
                sb.append(tmpa - tmpb);
            } else {
                tmpa = tmpa + 10;
                minuendArray[i + 1] = (char) (minuendArray[i + 1] - 1);
                sb.append(tmpa - tmpb);
            }
        }
        while (i < minuendArray.length) {
            sb.append(minuendArray[i++]);
        }
        String result = sb.reverse().toString();
        int j = 0;
        if (result.length() > 1) {
            for (; j < result.length(); j++) {
                if (result.charAt(j) != '0') {
                    break;
                }
            }
        }
        String realResult = result.substring(j);
        if (negative) {
            return "-" + realResult;
        }
        return realResult;
    }

    private boolean needReverse(String a, String b) {
        if (b.length() > a.length()) {
            return true;
        }
        return b.length() == a.length() && b.compareTo(a) > 0;

    }

    private String plus(String a, String b) {
        Objects.requireNonNull(a, "input must not be null");
        Objects.requireNonNull(b, "input must not be null");

        int remainder = 0;
        a = new StringBuilder(a).reverse().toString();
        b = new StringBuilder(b).reverse().toString();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i < a.length() & i < b.length(); i++) {
            int charA = a.charAt(i) - '0';
            int charB = b.charAt(i) - '0';
            sb.append((charA + charB + remainder) % 10);
            remainder = (charA + charB + remainder) / 10;
        }

        for (; i < a.length(); i++) {
            int charA = a.charAt(i) - '0';
            sb.append((charA + remainder) % 10);
            remainder = (charA + remainder) / 10;
        }

        for (; i < b.length(); i++) {
            int charB = b.charAt(i) - '0';
            sb.append((charB + remainder) % 10);
            remainder = (charB + remainder) / 10;
        }
        if (remainder != 0) {
            sb.append(remainder);
        }

        return sb.reverse().toString();
    }
}
