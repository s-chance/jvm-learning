package com.entropy.agent;

import java.lang.instrument.Instrumentation;

public class AgentMain {
    // premain方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain执行了....");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain执行了....");
    }
}
