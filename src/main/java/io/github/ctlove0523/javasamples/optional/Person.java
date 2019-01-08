package io.github.ctlove0523.javasamples.optional;

import java.util.Optional;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: Person
 *
 * @author: chentong
 * Date:     2019/1/8 22:55
 */
public class Person {
    private String name;
    private int age;
    private String password;

    public Person() {
    }

    public Person(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}