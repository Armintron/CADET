package src;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Random;
import info.debatty.java.stringsimilarity.*;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import me.tongfei.progressbar.*;
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

    private static String[] getListOfFiles() {
        File directory = new File(CORPI_ROOT);
        return directory.list();
    }

    private static int askInt(String request, Scanner input) {
        System.out.println(request + ":");
        System.out.print("> ");
        return input.nextInt();
    }

    private static int askMenuIndex(String request, String[] menu, Scanner input) {
        System.out.println(request + " (enter number from list below):");
        for (int i = 1; i <= menu.length; i++) {
            System.out.println(i + ". " + menu[i-1]);
        }
        System.out.print("> ");
        return input.nextInt() - 1;
    }

    private static String askString(String request, Scanner input) {
        System.out.println(request + ":");
        System.out.print("> ");
        return input.next();
    }

    private static TokenProvider askTokenProvider(Scanner input) {
        String[] corpi = getListOfFiles();
        int corpi_index = askMenuIndex("Corpus to use", corpi, input);
        File corpus = new File(CORPI_ROOT + File.separator + corpi[corpi_index]);
        return new TokenProvider(corpus);
    }

    // On each iteration, generate random search word (optional), spawn threads, run & time algorithm,
    // despawn threads, and compute stats
    private static double[] runIterations(boolean useRand, int iterations, String searchWord, int maxWordSize,
                                      StringDistance alg, String algName, TokenProvider provider, int numThreads) {
        double runningAverage = 0;
        double hi = Long.MIN_VALUE, lo = Long.MAX_VALUE;
        try (ProgressBar pb = new ProgressBar(algName + ", " + numThreads + " threads -", 
                                              iterations)) {
            for (int i = 1; i <= iterations; i++) {
                // generate random word
                if (useRand) {
                    Random r = new Random();
                    searchWord = "";
                    int wordSize = r.nextInt(maxWordSize) + 1;
                    for (int j = 0; j < wordSize; j++) {
                        searchWord += (char)('a' + r.nextInt(26));
                    }
                }
                AlgRunner algRunner = new AlgRunner(alg, provider, searchWord);
                long startTime = System.currentTimeMillis();
                Main.startAndWaitForThreads(algRunner, numThreads);
                long endTime = System.currentTimeMillis();
                double t = endTime - startTime;
                // computes running average to avoid potential overflow
                runningAverage = (i == 1) ? (t) : (runningAverage * ((double)i / (i + 1)) + (t / (i + 1)));
                hi = Math.max(hi, t);
                lo = Math.min(lo, t);
                provider.restartIterator();
                pb.step();
            }
        }

        return new double[] {runningAverage, lo, hi};
    }

    private static void printResults(double[] results) {
        System.out.printf("Average execution time was: %.3fms\n", results[0]);
        System.out.println("Fastest execution time was: " + (long)results[1] + "ms");
        System.out.println("Slowest execution time was: " + (long)results[2] + "ms");
    }

    public static void benchmarkOne() {
        try (Scanner input = new Scanner(System.in)) {
            TokenProvider provider = askTokenProvider(input);
            int algIndex = askMenuIndex("Algorithm to run", DropDownHandler.ALG_OPTIONS, input);
            StringDistance alg = DropDownHandler.ALGS[algIndex];
            String algName = DropDownHandler.ALG_OPTIONS[algIndex];
            int numThreads = askInt("Max number of threads to use", input);
            String searchWord = askString("Search word to use (enter \"?\" for random words on each iteration)", input);
            boolean useRand = searchWord.equals("?");
            int maxWordSize = -1;
            if (useRand) {
                maxWordSize = askInt("Max length of random words", input);
            }
            int iterations = askInt("Number of iterations to run for", input);

            double[] results = runIterations(useRand, iterations, searchWord, maxWordSize, alg, algName, provider, numThreads);
            printResults(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void benchmarkAll() {
        try (Scanner input = new Scanner(System.in)) {
            TokenProvider provider = askTokenProvider(input);
            int maxThreads = askInt("Max number of threads to use", input);
            int iterations = askInt("Number of iterations to run for", input);
            int maxWordSize = askInt("Max length of random words", input);
            String fileName = askString("File name to use (enter \"?\" for stdout)", input);

            PrintStream output = null;
            if (!fileName.equals("?")) {
                output = new PrintStream(fileName);
            }

            if (output != null) {
                output.println("Corpus used: " + provider.corpusName);
                output.println("Iterations used: " + iterations);
                output.println("Max random word length: " + maxWordSize);
                output.println();
            }
            System.out.println();

            for (int i = 0; i < DropDownHandler.ALG_OPTIONS.length; i++) {
                StringDistance alg = DropDownHandler.ALGS[i];
                String algName = DropDownHandler.ALG_OPTIONS[i];

                if (output != null) {
                    output.println(algName);
                    output.println("==================================");
                    output.println("# threads | avg(in ms)    lo    hi");
                }

                for (int j = 1; j <= maxThreads; j *= 2) {
                    double[] results = runIterations(true, iterations, "?", maxWordSize, alg, 
                                                     algName, provider, j);
                    if (output != null) {
                        output.printf("%9s | %10.3f %5s %5s\n", j, results[0], (long)results[1], (long)results[2]);
                    }
                    else {
                        printResults(results);
                    }
                }

                if (output != null) {
                    output.println("==================================");
                    output.println();
                }
            }

            System.out.println();
            System.out.println("Benchmarking complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
