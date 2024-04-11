package com.entropy.agent;

import com.entropy.agent.command.ClassCommand;
import com.entropy.agent.command.MemoryCommand;
import com.entropy.agent.command.ThreadCommand;
import com.entropy.agent.enhancer.AgentParam;
import com.entropy.agent.enhancer.TimingAdvice;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.Scanner;

public class AgentMain {
    // premain方法
    public static void premain(String agentArgs, Instrumentation inst) {
        // 使用bytebuddy增强类
        new AgentBuilder.Default()
                // 禁止bytebuddy处理时修改类名
                .disableClassFormatChanges()
                // 处理时使用retransform增强
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                // 打印错误日志
                .with(new AgentBuilder.Listener.WithTransformationsOnly(AgentBuilder.Listener.StreamWriting.toSystemOut()))
                // 匹配哪些类
                .type(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.RestController")
                        .or(ElementMatchers.named("org.springframework.web.bind.annotation.Controller"))))
                // 增强，使用MyAdvice通知，对所有方法都进行增强
                .transform((builder, typeDescription, classLoader, javaModule, protectionDomain) ->
                        builder.visit(Advice
                                        .withCustomMapping()
                                        .bind(AgentParam.class, agentArgs)
                                .to(TimingAdvice.class).on(ElementMatchers.any())))
                .installOn(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("菜单:\n" +
                    "1.查看内存使用情况\n" +
                    "2.生成堆内存快照\n" +
                    "3.打印栈信息\n" +
                    "4.打印类加载器\n" +
                    "5.打印类源码\n" +
                    "6.打印方法的参数和耗时\n" +
                    "7.退出\n");
            String input = scanner.next();
            switch (input) {
                case "1" -> MemoryCommand.printMemory();
                case "2" -> MemoryCommand.heapDump();
                case "3" -> ThreadCommand.printThreadInfo();
                case "4" -> ClassCommand.printAllClassLoader(inst);
                case "5" -> ClassCommand.printClassSourceCode(inst);
                case "6" -> ClassCommand.enhanceClass(inst);
                case "7" -> {
                    return;
                }
            }
        }
    }
}
