package org;

import java.util.concurrent.*;

public class Client implements Runnable{
    private final TaskManager manager;
    private final int operationsCount;
    private final int mode;

    public Client(TaskManager manager, int operationsCount, int mode) {
        this.manager = manager;
        this.operationsCount = operationsCount;
        this.mode = mode;
    }

    @Override
    public void run() {
        for (int i = 0; i < operationsCount; i++) {
            if (mode == 0) manager.completeTaskUnsafe();
            else if (mode == 1) manager.completeTaskSync();
            else manager.completeTaskLock();
        }
    }
}