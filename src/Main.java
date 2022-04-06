package src;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import info.debatty.java.stringsimilarity.*;
import src.Phonetic.Encoder;

import src.Benchmarking;

public class Main {

    public static void main(String[] args) {

        /*try (Scanner input = new Scanner(System.in)) {
            File inFile = new File("corpi\\beemovie.txt");
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
        }*/

        //Benchmarking.benchmarkOne();
        Benchmarking.benchmarkAll();
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

    /*public static void benchmark(int num, int numThreads, int alg, int seed, int wordSize, String word, File inFile) {
        try (Scanner input = new Scanner(System.in)) {
            File inFile = new File("corpi" + File.separator + "beemovie.txt");
            TokenProvider provider = new TokenProvider(inFile);
            Levenshtein l = new Levenshtein();
            String searchWord;
            do {
                System.out.println("How many Threads?");
                int numThread = input.nextInt();
                System.out.println("Search Word (enter \"?\" for random words on each iteration):");
                searchWord = input.next();
                boolean useRand = searchWord.equals("?");
                int maxWordSize = -1;
                if (useRand) {
                    System.out.println("Max length of random words?");
                    maxWordSize = input.nextInt();
                }

                System.out.println("How many iterations?");
                int iterations = input.nextInt();

                double runningAverage = 0;
                // BigDecimal runningAverage = BigDecimal.ZERO;
                double hi = Long.MIN_VALUE, lo = Long.MAX_VALUE;
                for (int i = 1; i <= iterations; i++) {
                    if (useRand) {
                        Random r = new Random();
                        searchWord = "";
                        int wordSize = r.nextInt(maxWordSize) + 1;
                        for (int j = 0; j < wordSize; j++) {
                            searchWord += (char)('a' + r.nextInt(26));
                        }
                    }
                    AlgRunner levenRunner = new AlgRunner(l, provider, searchWord);
                    long startTime = System.currentTimeMillis();
                    startAndWaitForThreads(levenRunner, numThread);
                    long endTime = System.currentTimeMillis();
                    // int t = (int)endTime - (int)startTime;
                    double t = endTime - startTime;
                    runningAverage = (i == 1) ? (t) : (runningAverage * ((double)i / (i + 1)) + (t / (i + 1)));
                    // runningAverage = runningAverage.add(BigDecimal.valueOf(t));
                    hi = Math.max(hi, t);
                    lo = Math.min(lo, t);
                    // outputScore(levenRunner.scoreMap.iterator());
                    provider.restartIterator();
                }
                // runningAverage = runningAverage.divide(BigDecimal.valueOf(iterations));
                System.out.printf("Average execution time was: %.3fms\n", runningAverage);
                System.out.println("Fastest execution time was: " + (long)lo + "ms");
                System.out.println("Slowest execution time was: " + (long)hi + "ms");

            } while (!searchWord.equalsIgnoreCase("EXIT"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
