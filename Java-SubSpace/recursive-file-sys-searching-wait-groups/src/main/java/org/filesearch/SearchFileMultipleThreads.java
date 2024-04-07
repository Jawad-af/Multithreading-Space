package org.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchFileMultipleThreads {
    static List<File> matches = new ArrayList<>(); // WE CAN USE THE CopyToWriteArrayList AND GET RID OF THE MANUAL SYNCH
    static Lock lock = new ReentrantLock(); // WE CAN USE SYNCHRONIZE BLOCK INSTEAD OF THE LOCK

    public void find(File file, String fileName, WaitGroup waitGroup) throws InterruptedException {
        System.out.println("Searching in " + file.getAbsolutePath());
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            lock.lock();
            if (f.getName().contains(fileName)) {
                matches.add(f);
            }
            lock.unlock();
            if (f.isDirectory()) {
                Thread t = new Thread(() -> {
                    try {
                        find(f, fileName, waitGroup);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                waitGroup.add(1);
                t.start();
            }
        }
        waitGroup.done();
    }

    // TO BE TESTED ALONE
    public static void main(String[] args) throws InterruptedException {
        WaitGroup waitGroup = new WaitGroup();
        SearchFileMultipleThreads multipleThreads = new SearchFileMultipleThreads();

        String fileName = "2. File Search Example.mp4";

        var start = System.currentTimeMillis();
        waitGroup.add(1);
        multipleThreads.find(new File("D:/IT/courses/java-courses/"), fileName, waitGroup);
        waitGroup.waitUntilDone();
        var end = System.currentTimeMillis();

        System.out.println();
        matches.forEach((f) -> System.out.println(f));
        System.out.println("The evaluation took : " + (end - start)/1000.0 + " seconds");
    }
}
