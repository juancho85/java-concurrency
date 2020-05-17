package com.juancho85;

import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
public class CallableMain {

    public static void main( String[] args ) throws Exception {
        final Integer input = 7;
        CallableFactorial factorial = new CallableFactorial(input);
        final Integer output = factorial.call();
        log.info(MessageFormat.format("Factorial of {0} is {1}", input, output));
    }
}
