package io.github.ctlove0523.javasamples.overrides;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: Student
 *
 * @author: chentong
 * Date:     2019/7/25 23:42
 */
public class Student extends Person {
    private String name;

    public Student(String name, String className) {
        super(name);
        this.name = className;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getName(String prefix) {
        return prefix + name;
    }
}
