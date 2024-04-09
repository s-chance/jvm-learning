package com.entropy.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AttachMain {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        // 获取进程列表，手动选择
        // 1.执行jps命令，打印进程列表
        Process jps = Runtime.getRuntime().exec("jps");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jps.getInputStream()));
        try (bufferedReader) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
        // 2.输入进程id
        Scanner scanner = new Scanner(System.in);
        String processId = scanner.next();

        // 获取进程虚拟机对象
        VirtualMachine vm = VirtualMachine.attach(processId);
        // 执行java agent中的agentmain方法
        vm.loadAgent("/home/entropy/jvm-learning/agent/target/agent-1.0-SNAPSHOT-jar-with-dependencies.jar");
    }
}
