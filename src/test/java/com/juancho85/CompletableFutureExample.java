package com.juancho85;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.text.MessageFormat;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class CompletableFutureExample {

    public static final String GENERIC_ERROR_MSG = "Oops, Something went wrong";

    @Test
    public void withExecutor() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        try {
            String expectedValue = "Hello";
            Executors.newCachedThreadPool().submit(() -> {
                Thread.sleep(500);
                log.info(MessageFormat.format("[{0}] executing completable future", Thread.currentThread().getName()));
                completableFuture.complete(expectedValue);
                return null;
            });
            String actualValue = completableFuture.get();
            log.info(MessageFormat.format("[{0}] Completable future returned {1}", Thread.currentThread().getName(), actualValue));
            assertEquals(expectedValue, actualValue);
        } catch (InterruptedException | ExecutionException e) {
            log.error(GENERIC_ERROR_MSG, e);
            fail();
        }
    }

    @Test
    public void withSupplierLambdaExpression() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        try {
            assertEquals("Hello", future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error(GENERIC_ERROR_MSG, e);
            fail();
        }
    }

    @Test
    @Timeout(2)
    public void withCompletedFuture() {
        try {
            String expectedResult = "I already know";
            // Launch calculation
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error(e);
                    fail();
                }
                return "Hello";
            });
            // Get result from elsewhere and complete the future
            completableFuture.complete("I already know");
            // I don't have to wait 10s to get the result
            String actualResult = completableFuture.get();
            assertEquals(expectedResult, actualResult);
        } catch (InterruptedException | ExecutionException e) {
            log.error(GENERIC_ERROR_MSG, e);
            fail();
        }
    }

    @Test
    public void withCancellationException() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });
        assertThrows(CancellationException.class, () -> completableFuture.get(), "Cancel does not affect CompletableFuture");
    }

    @Test
    public void withThenApplyFunction() {
        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
            CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");
            assertEquals("Hello World", future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error(GENERIC_ERROR_MSG, e);
            fail();
        }
    }

    @Test
    public void withThenAcceptConsumer() {
        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
            CompletableFuture<Void> future = completableFuture.thenAccept(s -> log.info("Computation returned: " + s));
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(GENERIC_ERROR_MSG, e);
            fail();
        }
    }

    @Test
    public void withThenRunRunnable() {
        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
            CompletableFuture<Void> future = completableFuture.thenRun(() -> log.info("Computation finished:"));
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(GENERIC_ERROR_MSG, e);
            fail();
        }
    }

}
