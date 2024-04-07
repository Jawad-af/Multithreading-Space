package org.filesearch;

import java.io.File;

public class Main {

    // TO BE TESTED AND COMPARED TOGETHER
    public static void main(String[] args) throws InterruptedException {
        WaitGroup waitGroup = new WaitGroup();
        SearchFileOneThread oneThread = new SearchFileOneThread();
        SearchFileMultipleThreads multipleThreads = new SearchFileMultipleThreads();

        String fileName = "2. File Search Example.mp4"; // PUT THE NAME OF THE FILE YOU WANT TO SEARCH FOR

        var startSingle = System.currentTimeMillis();
        oneThread.find(new File("D:/"), fileName);
        var endSingle = System.currentTimeMillis();

        var startMultiple = System.currentTimeMillis();
        waitGroup.add(1);
        multipleThreads.find(new File("D:/"), fileName, waitGroup);
        waitGroup.waitUntilDone();
        var endMultiple = System.currentTimeMillis();

        System.out.println();
        multipleThreads.matches.forEach((f) -> System.out.println(f));
        System.out.println();
        oneThread.matches.forEach((f) -> System.out.println(f));

        System.out.println("\nThe evaluation of single thread took : " + (endSingle - startSingle)/1000.0 + " seconds");
        System.out.println("The evaluation of multiple threads took : " + (endMultiple - startMultiple)/1000.0 + " seconds");

    }
}
