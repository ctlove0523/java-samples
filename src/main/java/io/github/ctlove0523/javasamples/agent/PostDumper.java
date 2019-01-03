package io.github.ctlove0523.javasamples.agent;

import java.lang.instrument.Instrumentation;

/**
 * PostDumper
 *
 * @author c00382802
 * 2018/12/13
 */
public class PostDumper {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("post agent loaded");
        inst.addTransformer(new Transformer());
    }
}
