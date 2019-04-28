package io.github.ctlove0523.javasamples.retry;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: UserState
 *
 * @author: chentong
 * Date:     2019/4/21 13:59
 */


public class UserState {
    private String name;
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserState{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
