package src;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import info.debatty.java.stringsimilarity.*;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import src.gui.DropDownHandler;


public class Benchmarking {

    public final static String[] ALG_OPTIONS = {
        "Cosine",
        "Damerau",
        "Jaccard",
        "JaroWinkler",
        "Levenshtein",
        "LongestCommonSubsequence",
        "MetricLCS",
        "NGram",
        "NormalizedLevenshtein",
        "OptimalStringAlignment",
        "QGram",
        "RatcliffObershelp",
        "SorensenDice",
    };
    public final static StringDistance[] ALGS = {
            new Cosine(),
            new Damerau(),
            new Jaccard(),
            new JaroWinkler(),
            new Levenshtein(),
            new LongestCommonSubsequence(),
            new MetricLCS(),
            new NGram(),
            new NormalizedLevenshtein(),
            new OptimalStringAlignment(),
            new QGram(),
            new RatcliffObershelp(),
            new SorensenDice()
    };

    public static final String CORPI_ROOT = "corpi";

    public static String[] getListOfFiles() {
        File directory = new File(CORPI_ROOT);
        return directory.list();
    }

    private static TokenProvider askTokenProvider() {
        TokenProvider provider = null;
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Corpus to use (enter number from list below):");
            String[] corpi = getListOfFiles();
            for (int i = 1; i <= corpi.length; i++) {
                System.out.println(i + ". " + corpi[i-1]);
            }
            System.out.print("> ");
            File corpus = new File(CORPI_ROOT + File.separator + corpi[input.nextInt() - 1]);
            provider = new TokenProvider(corpus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provider;
    }

    private static int askInt(String request, Scanner input) {
        System.out.println(request);
        System.out.print("> ");
        return input.nextInt();
    }

    private static void runIterations(boolean useRand, int iterations, String searchWord, int maxWordSize,
                                      StringDistance alg, TokenProvider provider, int numThreads) {
        double runningAverage = 0;
        double hi = Long.MIN_VALUE, lo = Long.MAX_VALUE;
        // try (ProgressBar pb = new ProgressBar(""))
        for (int i = 1; i <= iterations; i++) {
            if (useRand) {
                Random r = new Random();
                searchWord = "";
                int wordSize = r.nextInt(maxWordSize) + 1;
                for (int j = 0; j < wordSize; j++) {
                    searchWord += (char)('a' + r.nextInt(26));
                }
            }
            AlgRunner levenRunner = new AlgRunner(alg, provider, searchWord);
            long startTime = System.currentTimeMillis();
            Main.startAndWaitForThreads(levenRunner, numThreads);
            long endTime = System.currentTimeMillis();
            double t = endTime - startTime;
            runningAverage = (i == 1) ? (t) : (runningAverage * ((double)i / (i + 1)) + (t / (i + 1)));
            hi = Math.max(hi, t);
            lo = Math.min(lo, t);
            provider.restartIterator();
        }

        System.out.printf("Average execution time was: %.3fms\n", runningAverage);
        System.out.println("Fastest execution time was: " + (long)lo + "ms");
        System.out.println("Slowest execution time was: " + (long)hi + "ms");
    }

    public static void benchmarkOne() {
        try (Scanner input = new Scanner(System.in)) {
            TokenProvider provider = askTokenProvider();

            System.out.println("Algorithm to run (enter number from list below):");
            for (int i = 1; i <= DropDownHandler.ALG_OPTIONS.length; i++) {
                System.out.println(i + ". " + DropDownHandler.ALG_OPTIONS[i-1]);
            }
            System.out.print("> ");
            StringDistance alg = DropDownHandler.ALGS[input.nextInt()];

            int numThreads = askInt("Max number of threads to use:", input);

            System.out.println("Search word to use (enter \"?\" for random words on each iteration):");
            System.out.print("> ");
            String searchWord = input.next();
            boolean useRand = searchWord.equals("?");
            int maxWordSize = -1;
            if (useRand) {
                maxWordSize = askInt("Max length of random words:", input);
            }

            int iterations = askInt("Number of iterations to run for:", input);

            runIterations(useRand, iterations, searchWord, maxWordSize, alg, provider, numThreads);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void benchmarkAll() {
        try (Scanner input = new Scanner(System.in)) {
            TokenProvider provider = askTokenProvider();

            int maxThreads = askInt("Max number of threads to use:", input);
            int iterations = askInt("Number of iterations to run for:", input);
            int maxWordSize = askInt("Max length of random words:", input);

            for (StringDistance alg : DropDownHandler.ALGS) {
                for (int i = 1; i <= maxThreads; i *= 2) {
                    runIterations(true, iterations, "?", maxWordSize, alg, provider, i);
                }
            }
        }
    }*/
}
