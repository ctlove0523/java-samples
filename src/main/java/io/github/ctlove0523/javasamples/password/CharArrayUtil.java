package io.github.ctlove0523.javasamples.password;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CharArrayUtil {

    public static Map<Character, Integer> charArrayToMap(char[] chars) {
        if (chars == null || chars.length == 0) {
            return new HashMap<>();
        }

        Map<Character, Integer> charMap = new HashMap<>();
        for (char ch : chars) {
            charMap.compute(ch, (character, integer) -> integer == null?1:integer+1);
        }

        return charMap;
    }
}
