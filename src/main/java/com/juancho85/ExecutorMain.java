package com.juancho85;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Log4j2
public class ExecutorMain {

    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        final Future<Integer> factorialFiveFuture = executorService.submit(new CallableFactorial(5));
        Integer calculatedFactorialOfFive = factorialFiveFuture.get(200, TimeUnit.MILLISECONDS);
        log.info(MessageFormat.format("Calculated factorial of 5 was {0}", calculatedFactorialOfFive));


        final int input1 = 7;
        final int input2 = 6;
        final int input3 = 5;
        List<Callable<Integer>> callableTasks = new ArrayList<>();
        callableTasks.add(new CallableFactorial(input1));
        callableTasks.add(new CallableFactorial(input2));
        callableTasks.add(new CallableFactorial(input3));

        List<Future<Integer>> factorialCalls = executorService.invokeAll(callableTasks);
        log.info(MessageFormat.format("Factorial1 of {0} is {1}", input1, factorialCalls.get(0).get()));
        log.info(MessageFormat.format("Factorial1 of {0} is {1}", input2, factorialCalls.get(1).get()));
        log.info(MessageFormat.format("Factorial1 of {0} is {1}", input3, factorialCalls.get(2).get()));

        // Ensure the executor service is shut down: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
        // To avoid unused threads to be kept
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }


    }
}
