package org.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchFileMultipleThreads {
    static List<File> matches = new ArrayList<>(); // WE CAN USE THE CopyToWriteArrayList AND GET RID OF THE MANUAL SYNCH
    static Lock lock = new ReentrantLock(); // WE CAN USE SYNCHRONIZE BLOCK INSTEAD OF THE LOCK

    public void find(File file, String fileName) throws InterruptedException {
        System.out.println("Searching in " + file.getAbsolutePath());
        List<Thread> threads = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            // Synchronized(This) {
            lock.lock();
            if (f.getName().contains(fileName)) {
                matches.add(f);
            }
            lock.unlock();
            // }
            if (f.isDirectory()) {
                Thread t = new Thread(() -> {
                    try {
                        find(f, fileName);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                threads.add(t);
                t.start();
            }
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    // TO BE TESTED ALONE
    public static void main(String[] args) throws InterruptedException {
        SearchFileMultipleThreads multipleThreads = new SearchFileMultipleThreads();

        String fileName = "2. File Search Example.mp4";

        var start = System.currentTimeMillis();
        multipleThreads.find(new File("D:/IT/courses/java-courses/"), fileName);
        var end = System.currentTimeMillis();

        System.out.println();
        matches.forEach((f) -> System.out.println(f));
        System.out.println("The evaluation took : " + (end - start)/1000.0 + " seconds");
    }
}
