package org.urllettercount;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class CountingLettersWithOneThread {
    public static void countLettersWithOneThread(URL url, HashMap<Character, Integer> lettersCount) throws IOException {
        var stream = url.openStream();
        String text = new String(stream.readAllBytes());

        for (char c : text.toCharArray()) {
            char letter = Character.toLowerCase(c);
            if(lettersCount.containsKey(c))
                lettersCount.put(c, lettersCount.get(c) + 1);
        }
        stream.close();
    }

    public static void main(String[] args) throws IOException {
        HashMap<Character, Integer> letterCount = new HashMap<>();
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            letterCount.put(c, 0);
        }

        var start = System.currentTimeMillis();
        for (int i = 1000; i < 1050; i++) {
            var url = new URL("https://www.rfc-editor.org/rfc/rfc%s.txt".formatted(i));
            countLettersWithOneThread(url, letterCount);
        }
        var end = System.currentTimeMillis();
        letterCount.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("\n\nEvaluation Time = " + (end - start)/1000.0);
    }
}