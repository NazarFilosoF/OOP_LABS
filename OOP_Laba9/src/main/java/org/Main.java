package org;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        int threadsCount = 5;
        int operationsPerThread = 1000;
        int expectedTotal = threadsCount * operationsPerThread;

        System.out.println("Ожидаемое количество задач: " + expectedTotal);

        //БЕЗ СИНХРОНИЗАЦИИ
        System.out.println("\n=== 1. Без синхронизации (Race Condition) ===");
        TaskManager unsafeManager = new TaskManager();
        runWithThreads(unsafeManager, threadsCount, operationsPerThread, 0);
        System.out.println("Выполнено задач: " + unsafeManager.getCompletedTasks());
        System.out.println("Потеряно задач: " + (expectedTotal - unsafeManager.getCompletedTasks()));

        //SYNCHRONIZED
        System.out.println("\n=== 2. С synchronized ===");
        TaskManager syncManager = new TaskManager();
        long startSync = System.currentTimeMillis();
        runWithThreads(syncManager, threadsCount, operationsPerThread, 1);
        long endSync = System.currentTimeMillis();
        System.out.println("Выполнено задач: " + syncManager.getCompletedTasks());
        System.out.println("Время выполнения: " + (endSync - startSync) + " мс");

        //REENTRANTLOCK
        System.out.println("\n=== 3. С ReentrantLock ===");
        TaskManager lockManager = new TaskManager();
        long startLock = System.currentTimeMillis();
        runWithThreads(lockManager, threadsCount, operationsPerThread, 2);
        long endLock = System.currentTimeMillis();
        System.out.println("Выполнено задач: " + lockManager.getCompletedTasks());
        System.out.println("Время выполнения: " + (endLock - startLock) + " мс");

        //(ExecutorService)
        System.out.println("\n=== 4. Пул потоков (ExecutorService) с ReentrantLock ===");
        TaskManager poolManager = new TaskManager();
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        long startPool = System.currentTimeMillis();

        for (int i = 0; i < threadsCount; i++) {
            executor.submit(new Client(poolManager, operationsPerThread, 2));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        long endPool = System.currentTimeMillis();
        System.out.println("Выполнено задач: " + poolManager.getCompletedTasks());
        System.out.println("Время выполнения: " + (endPool - startPool) + " мс");
    }
    private static void runWithThreads(TaskManager manager, int count, int ops, int mode) throws InterruptedException {
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new Client(manager, ops, mode));
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
}
