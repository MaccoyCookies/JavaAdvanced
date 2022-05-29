package com.maccoy.advanced.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class NewThreadReturn {

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 01
        FutureTask<Integer> futureTask = new FutureTask<>(NewThreadReturn::sum);
        Thread thread = new Thread(futureTask);
        thread.start();
        Integer method01 = futureTask.get();
        System.out.println("method01 = " + method01);

        // 02
        ExecutorService method02ExecutorService = Executors.newFixedThreadPool(16);
        Integer method02 = method02ExecutorService.submit(NewThreadReturn::sum).get();
        method02ExecutorService.shutdown();
        System.out.println("method02 = " + method02);

        // 03
        Integer method03 = CompletableFuture.supplyAsync(NewThreadReturn::sum).get();
        System.out.println("method03 = " + method03);

        // 04
        ExecutorService method04ExecutorService = Executors.newFixedThreadPool(16);
        Integer method04 = CompletableFuture.supplyAsync(NewThreadReturn::sum, method04ExecutorService).get();
        method04ExecutorService.shutdown();
        System.out.println("method04 = " + method04);

        // 05
        Method05Runnable method05Runnable = new Method05Runnable();
        Thread method05Thread = new Thread(method05Runnable);
        method05Thread.start();
        method05Thread.join();
        System.out.println("method05 = " + method05Runnable.getResult());

        // 06
        Method06Thread method06Thread = new Method06Thread();
        method06Thread.start();
        method06Thread.join();
        System.out.println("method06 = " + method06Thread.getResult());

        // 07
        Method05Runnable method07Runnable = new Method05Runnable();
        ExecutorService method07ExecutorService = Executors.newFixedThreadPool(16);
        method07ExecutorService.execute(method07Runnable);
        method07ExecutorService.shutdown();
        while (!method07ExecutorService.isTerminated()) {
            Thread.sleep(50);
        }
        System.out.println("method07 = " + method07Runnable.getResult());

        // 08
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Method08Runnable method08Runnable = new Method08Runnable(countDownLatch);
        ExecutorService method08ExecutorService = Executors.newFixedThreadPool(16);
        method08ExecutorService.execute(method08Runnable);
        countDownLatch.await();
        method08ExecutorService.shutdown();
        System.out.println("method08 = " + method08Runnable.getResult());

        // 09
        ExecutorService method09ExecutorService = Executors.newFixedThreadPool(16);
        List<Future<Integer>> futures = method09ExecutorService
                .invokeAll(Collections.singletonList(NewThreadReturn::sum));
        method09ExecutorService.shutdown();
        System.out.println("method09 = " + futures.get(0).get());

        // 10
        Method10Runnable method10Runnable = new Method10Runnable();
        Thread method10Thread = new Thread(method10Runnable);
        synchronized (method10Thread) {
            method10Thread.wait();
        }
        System.out.println("method10 = " + method10Runnable.getResult());
    }

    static class Method05Runnable implements Runnable {
        private int result = 0;

        @Override
        public void run() {
            result = sum();
        }

        public int getResult() {
            return result;
        }
    }

    static class Method06Thread extends Thread {
        private int result = 0;

        @Override
        public void run() {
            result = sum();
        }

        public int getResult() {
            return result;
        }
    }

    static class Method08Runnable implements Runnable {
        private int result = 0;
        private final CountDownLatch countDownLatch;
        public Method08Runnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            result = sum();
            countDownLatch.countDown();
        }
        public int getResult() {
            return result;
        }
    }

    static class Method10Runnable implements Runnable {
        private int result = 0;
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                result = sum();
            }
        }
        public int getResult() {
            return result;
        }
    }
}



