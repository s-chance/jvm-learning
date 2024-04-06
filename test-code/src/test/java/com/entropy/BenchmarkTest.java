package com.entropy;

import com.entropy.performance.controller.UserController;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

// 执行5轮预热，每次持续1秒
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
// 执行一次测试
@Fork(value = 1, jvmArgsAppend = {"-Xms1g", "-Xmx1g"})
// 显示平均时间，单位豪秒
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkTest {

    private UserController userController;
    private ApplicationContext context;

    // 初始化将Springboot容器启动，端口号随机
    @Setup
    public void setUp() {
        this.context = new SpringApplication(Application.class).run();
        userController = this.context.getBean(UserController.class);
    }

    // 启动测试用例进行测试
    @Test
    public void executeJmhRunner() throws RunnerException {
        new Runner(new OptionsBuilder()
                .shouldDoGC(true)
                .forks(0)
                .resultFormat(ResultFormatType.JSON)
                .shouldFailOnError(true)
                .build()).run();
    }

    // 使用黑洞消费，避免JIT消除代码
    @Benchmark
    public void test1(final Blackhole bh) {
        bh.consume(userController.user1());
    }
    @Benchmark
    public void test2(final Blackhole bh) {
        bh.consume(userController.user2());
    }
    @Benchmark
    public void test3(final Blackhole bh) {
        bh.consume(userController.user3());
    }
    @Benchmark
    public void test4(final Blackhole bh) {
        bh.consume(userController.user4());
    }
    @Benchmark
    public void test5(final Blackhole bh) {
        bh.consume(userController.user5());
    }
}
