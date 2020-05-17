package com.juancho85;

import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
public class CompletableFutureMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            log.info(MessageFormat.format("[{0}] executing completable future", Thread.currentThread().getName()));
            completableFuture.complete("Hello");
            return null;
        });
        log.info(MessageFormat.format("[{0}] Completable future returned {1}", Thread.currentThread().getName(), completableFuture.get()));

        // A shorter version with a Supplier lambda expression
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        assertEquals("Hello", future.get());
    }
}
