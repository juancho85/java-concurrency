package com.juancho85;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
public class RunnableTask implements Runnable {
    private Thread myThread;
    private String myThreadName;

    public RunnableTask(String myThreadName) {
        this.myThreadName = myThreadName;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info(MessageFormat.format("[{0}] Thread running", myThreadName));
        for (int i = 0; i < 4; i++) {
            log.info(MessageFormat.format("[{0}] Iteration {1}", myThreadName, i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(MessageFormat.format("[{0}] has been interrupted ", myThreadName), e);
                throw e;
            }
        }
    }

    public void start() {
        if (myThread == null) {
            myThread = new Thread(this, myThreadName);
            myThread.start();
            log.info(MessageFormat.format("[{0}] Started", myThreadName));
        }
    }
}
