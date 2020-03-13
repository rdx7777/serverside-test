package io.github.rdx7777.serversidetest.generators;

import java.util.Random;

public class WordGenerator {

    private static Random random = new Random();

    public static String getRandomWord() {
        char[] word = new char[random.nextInt(8) + 3];
        for (int i = 0; i < word.length; i++) {
            word[i] = (char) ('a' + random.nextInt(20));
        }
        return new String(word);
    }
}
