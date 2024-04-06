package org.urllettercount;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class CountingLettersWithMultipleThreads {
    public void countLettersWithMultipleThreads(URL url, ConcurrentHashMap<Character, Integer> lettersCount) throws IOException {
        var stream = url.openStream();
        String text = new String(stream.readAllBytes());

        synchronized (this){
            for (char c : text.toCharArray()) {
                char letter = Character.toLowerCase(c);
                if(lettersCount.containsKey(c)){
                    lettersCount.put(c, lettersCount.get(c) + 1);
                }
            }
        }
        stream.close();
    }

    public static void main(String[] args) throws RuntimeException, MalformedURLException {
        CountingLettersWithMultipleThreads m = new CountingLettersWithMultipleThreads();
        ConcurrentHashMap<Character, Integer> letterCount = new ConcurrentHashMap<>();
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            letterCount.put(c, 0);
        }

        var start = System.currentTimeMillis();
        for (int i = 1000; i < 1050; i++) {
            var url = new URL("https://www.rfc-editor.org/rfc/rfc%s.txt".formatted(i));
            new Thread(() -> {
                try {
                    m.countLettersWithMultipleThreads(url, letterCount);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        var end = System.currentTimeMillis();
        letterCount.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("\n\nEvaluation Time = " + (end - start)/1000.0);
    }
}
