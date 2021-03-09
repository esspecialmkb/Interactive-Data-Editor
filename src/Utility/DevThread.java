/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author esspe
 * 
 * This main class will be used for development
 */
public class DevThread {
    
    private static final Queue queue = new ConcurrentLinkedQueue();
    private static final long startMillis = System.currentTimeMillis();
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    public static class Consumer implements Runnable {
        private String ANSI_COLOR;
        
        public Consumer(int i){
            switch(i){
                case 0:
                    ANSI_COLOR = ANSI_RED;
                    break;
                case 1:
                    ANSI_COLOR = ANSI_GREEN;
                    break;
                case 2:
                    ANSI_COLOR = ANSI_YELLOW;
                    break;
                case 3:
                    ANSI_COLOR = ANSI_BLUE;
                    break;
                case 4:
                    ANSI_COLOR = ANSI_PURPLE;
                    break;
            }
        }
        
        public void run() {
    
            while (System.currentTimeMillis() < (startMillis + 10000)) {
                synchronized (queue) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                    
                        e.printStackTrace();
                    }
                }
    
                if (!queue.isEmpty()) {
                    Integer integer = (Integer) queue.poll();
                    System.out.println(ANSI_COLOR + "[" + Thread.currentThread().getName() + "]: " + integer + ANSI_RESET);
                }
            }
        }
    }
    
    public static class Producer implements Runnable {
        public void run() {
            int i = 0;
            while (System.currentTimeMillis() < (startMillis + 10000)) {
                queue.add(i++);
                    synchronized (queue) {
                    queue.notify();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (queue) {
                queue.notifyAll();
            }
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        Thread[] consumerThreads = new Thread[5];
        
        for (int i = 0; i < consumerThreads.length; i++) {
            consumerThreads[i] = new Thread(new Consumer(i), "consumer-" + i);
            consumerThreads[i].start();
        }
        
        Thread producerThread = new Thread(new Producer(), "producer");
        producerThread.start();
        for (int i = 0; i < consumerThreads.length; i++) {
            consumerThreads[i].join();
        }
        producerThread.join();
    }
    
}
