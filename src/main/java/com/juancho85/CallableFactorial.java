package com.juancho85;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;
import java.util.concurrent.Callable;

@Log4j2
@AllArgsConstructor
public class CallableFactorial implements Callable<Integer> {
    int number;

    @Override
    public Integer call() throws Exception {
        log.info(MessageFormat.format("[{0}] Factorial Callable", Thread.currentThread().getName()));
        int fact = 1;
        for(int count = number; count > 1; count--) {
            fact = fact * count;
        }
        return fact;
    }
}
