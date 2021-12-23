package io.github.ctlove0523.javasamples.password;

import java.util.Map;

public class AtLeastOneLowercaseLetterPolicy implements PasswordPolicy {
    private static final char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    @Override
    public boolean pass(char[] password) {
        Map<Character, Integer> charMap = CharArrayUtil.charArrayToMap(password);
        for (char ch : LOWERCASE) {
            if (charMap.containsKey(ch)) {
                return true;
            }
        }
        return false;
    }
}
