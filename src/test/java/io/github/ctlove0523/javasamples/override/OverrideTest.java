package io.github.ctlove0523.javasamples.override;

import io.github.ctlove0523.javasamples.overrides.Person;
import io.github.ctlove0523.javasamples.overrides.Student;
import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: OverrideTest
 *
 * @author: chentong
 * Date:     2019/7/25 23:45
 */
public class OverrideTest {

    @Test
    public void test_override_success() {
        Person person = new Person("A");
        Person student = new Student("A", "B");
        Assert.assertEquals("A", person.getName());
        Assert.assertEquals("B", student.getName());
    }

    @Test
    public void test_overload_success() {
        Student student = new Student("A", "B");
        Assert.assertEquals("B",student.getName());
        Assert.assertEquals("prefix" + "B",student.getName("prefix"));
    }
}
