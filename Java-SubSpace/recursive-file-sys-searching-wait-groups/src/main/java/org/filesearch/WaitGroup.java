package org.filesearch;

public class WaitGroup {

    private int threadsCount;

    public synchronized void add(int count) {
        threadsCount += count;
    }

    public synchronized void done() {
        if (threadsCount > 0) {
            threadsCount--;
        }
        if (threadsCount == 0) {
            notifyAll();
        }
    }

    public synchronized void waitUntilDone() throws InterruptedException {
        while (threadsCount > 0) {
            wait();
        }
    }
}
