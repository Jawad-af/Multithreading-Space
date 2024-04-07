package org.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFileOneThread {

    static List<File> matches = new ArrayList<>();
    public void find(File file, String fileName) {
        System.out.println("Searching in " + file.getAbsolutePath());
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.getName().contains(fileName)) {
                matches.add(f);
            }
            if (f.isDirectory()) {
                find(f, fileName);
            }
        }
    }

    // TO BE TESTED ALONE
    public static void main(String[] args) {
        SearchFileOneThread oneThread = new SearchFileOneThread();
        String fileName = "2. File Search Example.mp4";

        var start = System.currentTimeMillis();
        oneThread.find(new File("D:/IT/courses/java-courses/"), fileName);
        var end = System.currentTimeMillis();

        System.out.println();
        matches.forEach((f) -> System.out.println(f));
        System.out.println("The evaluation took : " + (end - start)/1000.0 + " seconds");
    }
}