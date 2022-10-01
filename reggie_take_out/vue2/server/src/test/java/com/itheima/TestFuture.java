package com.itheima;

import com.itheima.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 同步接收结果，异步接收结果
public class TestFuture {
    static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        async();
    }

    // 异步方式
    static void async() {
        LoggerUtil.get().debug("之前...");

        CompletableFuture.supplyAsync(() -> {
            LoggerUtil.get().debug("开始计算");
            sleep2s();
            LoggerUtil.get().debug("结束计算");
            return "123";
        }, pool).thenAcceptAsync(result->LoggerUtil.get().debug(result), pool);

        LoggerUtil.get().debug("之后...");
    }

    // 同步方式
    static void sync() throws InterruptedException, ExecutionException {
        LoggerUtil.get().debug("之前...");

        String result = CompletableFuture.supplyAsync(() -> {
            LoggerUtil.get().debug("开始计算");
            sleep2s();
            LoggerUtil.get().debug("结束计算");
            return "123";
        }, pool).get();

        LoggerUtil.get().debug(result);
        LoggerUtil.get().debug("之后...");
    }

    static void sleep2s() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
