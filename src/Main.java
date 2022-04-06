package src;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import info.debatty.java.stringsimilarity.*;
import src.Phonetic.Encoder;

public class Main {
    // TODO use ResultStat wrapper to normalize scores and send to output score
    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            File inFile = new File("corpi" + File.separator + "beemovie.txt");
            TokenProvider provider = new TokenProvider(inFile);
            Levenshtein l = new Levenshtein();
            String searchWord;
            do {
                System.out.println("How many Threads?");
                int numThread = input.nextInt();
                System.out.println("Search Word:");
                searchWord = input.next();
                AlgRunner levenRunner = new AlgRunner(l, provider, searchWord);
                startAndWaitForThreads(levenRunner, numThread);
                outputScore(levenRunner.scoreMap.iterator());
                provider.restartIterator();
            } while (!searchWord.equalsIgnoreCase("EXIT"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void outputScore(Iterator iterator) {
        System.out.println("Output:");
        for (int i = 0; i < 10; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            System.out.println(iterator.next().toString());
        }

    }

    public static void startAndWaitForThreads(Runnable r, int num) {
        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; i++) {
            threads[i] = new Thread(r, "" + i);
            threads[i].start();
        }
        for (int i = 0; i < num; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
