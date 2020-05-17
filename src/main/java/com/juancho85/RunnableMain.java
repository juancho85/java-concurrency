package com.juancho85;

public class RunnableMain {

    public static void main( String[] args ) throws InterruptedException {
        RunnableTask thread1 = new RunnableTask("Thread 1");
        thread1.start();
        RunnableTask thread2 = new RunnableTask("Thread 2");
        thread2.start();
    }

}
