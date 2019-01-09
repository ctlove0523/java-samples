package io.github.ctlove0523.javasamples.stream.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: FilterService
 *
 * @author: chentong
 * Date:     2019/1/9 8:48
 */
public class FilterService {

    public static List<Integer> findEvenNumbersUseStream(List<Integer> numbers) {
        Predicate<Integer> condition = o -> o.intValue() % 2 == 0;
        return numbers.stream().filter(condition).collect(Collectors.toList());
    }

    public static List<Integer> findEvenNumbersUseStreaLambda(List<Integer> numbers) {
        return numbers.stream()
                .filter(integer -> integer.intValue() % 2 == 0).collect(Collectors.toList());
    }
}
