package com.juancho85;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class RecursiveTaskMain {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(executorService.submit(new CallableFactorial(5)).get()));
        int b = executorService.submit(new CallableFactorial(5)).get();
        log.info(a.get() + b);
        executorService.shutdown();
    }
}
