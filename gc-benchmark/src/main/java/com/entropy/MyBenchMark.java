package com.entropy;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 执行5轮预热，每次持续2秒
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
// 输出毫秒单位
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// 统计方法执行的平均耗时
@BenchmarkMode(Mode.AverageTime)
// java -jar benchmark.jar -rf json
@State(Scope.Benchmark)
public class MyBenchMark {

    // 每次测试的对象大小4KB和4MB
    @Param({"4", "4096"})
    int perSize;

    private void test(Blackhole bh) {

        // 每次循环创建占堆内存60%的对象 JMX获取到Java运行中的实时数据
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 获取堆内存大小
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        // 获取到剩余的堆内存大小
        long heapSize = (long) ((heapMemoryUsage.getMax() - heapMemoryUsage.getUsed()) * 0.6);
        // 计算循环次数
        long size = heapSize / (1024 * perSize);

        for (int i = 0; i < 4; i++) {
            List<byte[]> objects = new ArrayList<>((int) size);
            for (int j = 0; j < size; j++) {
                objects.add(new byte[1024 * perSize]);
            }
            bh.consume(objects);
        }
    }

    @Benchmark
    @Fork(value = 1, jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseSerialGC"})
    public void serialGC(Blackhole bh) {
        test(bh);
    }

    @Benchmark
    @Fork(value = 1, jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseParallelGC"})
    public void parallelGC(Blackhole bh) {
        test(bh);
    }

    @Benchmark
    @Fork(value = 1, jvmArgsAppend = {"-Xms4g", "-Xmx4g"})
    public void g1(Blackhole bh) {
        test(bh);
    }

    @Benchmark
    @Fork(value = 1, jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseShenandoahGC"})
    public void shenandoahGC(Blackhole bh) {
        test(bh);
    }

    // -XX:+UseZGC -XX:+ZGenerational
    @Benchmark
    @Fork(value = 1, jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseZGC"})
    public void ZGC(Blackhole bh) {
        test(bh);
    }

    @Benchmark
    @Fork(value = 1, jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseZGC", "-XX:+ZGenerational"})
    public void ZGCGenerational(Blackhole bh) {
        test(bh);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchMark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
