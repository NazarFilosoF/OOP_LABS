package org;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskManager {
    private int completedTasks = 0;
    private final Lock lock = new ReentrantLock();

    //БЕЗ СИНХРОНИЗАЦИИ
    public void completeTaskUnsafe() {
        completedTasks++;
    }

    //SYNCHRONIZED
    public synchronized void completeTaskSync() {
        completedTasks++;
    }

    //REENTRANTLOCK
    public void completeTaskLock() {
        lock.lock();
        try {
            completedTasks++;
        } finally {
            lock.unlock();
        }
    }

    public int getCompletedTasks() {
        return completedTasks;
    }
}