package io.github.ctlove0523.javasamples.functions;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

public class PredicateExample {

    @Test
    public void test_predicate_success() {
        Predicate<Integer> predicate = integer -> integer > 10;
        Assert.assertFalse(predicate.test(1));
        Assert.assertTrue(predicate.test(11));

        Predicate<Integer> andPredicate = ((Predicate<Integer>) integer -> integer > 10).and(integer -> integer <20);
        Assert.assertTrue(andPredicate.test(15));
        Assert.assertFalse(andPredicate.test(10));
        Assert.assertFalse(andPredicate.test(20));

        Predicate<Integer> negatePredicate = andPredicate.negate();
        Assert.assertTrue(negatePredicate.test(9));
        Assert.assertTrue(negatePredicate.test(21));
        Assert.assertFalse(negatePredicate.test(15));

        Predicate<Integer> orPredicate = ((Predicate<Integer>) integer -> integer < 5).or(integer -> integer >10);
        Assert.assertTrue(orPredicate.test(4));
        Assert.assertTrue(orPredicate.test(15));
        Assert.assertFalse(orPredicate.test(8));

        Predicate<Integer> equalPredicate = Predicate.isEqual(10);
        Assert.assertFalse(equalPredicate.test(9));
        Assert.assertFalse(equalPredicate.test(11));
        Assert.assertTrue(equalPredicate.test(10));
    }
}
