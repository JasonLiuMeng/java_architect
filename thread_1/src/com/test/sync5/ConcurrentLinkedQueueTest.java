package com.test.sync5;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentLinkedQueueTest {
		

    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private static int count = 100;
    private static int count2 = 3; // 线程个数
    private static CountDownLatch cd = new CountDownLatch(count2);
    
    public static void dothis() {
        for (int i = 0; i < count; i++) {
            queue.offer(i);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        ConcurrentLinkedQueueTest.dothis();
        for (int i = 0; i < count2; i++) {
            es.submit(new Poll());
        }
        cd.await();
        System.out.println("cost time "
                + (System.currentTimeMillis() - timeStart) + "ms");
        es.shutdown();
    }
    
    static class Poll implements Runnable {
        @Override
        public void run() {
	        while (queue.size()>0) {
//            while (!queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " - " + queue.poll());
            }
            cd.countDown();
        }
    }
	
}
