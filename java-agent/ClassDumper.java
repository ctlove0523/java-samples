package io.java.agent.javaagent;

import java.lang.instrument.Instrumentation;

/**
 * ClassDumper
 *
 * @author c00382802
 * 2018/12/6
 */
public class ClassDumper {

    /**
     * A java agent must have a premain method which acts as the entry-point
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agent loaded");
        // Register our transformer
        inst.addTransformer(new Transformer());
    }
}
