package src;

import java.util.concurrent.ConcurrentHashMap;
import info.debatty.java.stringsimilarity.interfaces.*;
import info.debatty.java.stringsimilarity.*;

public class AlgRunner implements Runnable {

    ConcurrentHashMap<String, Double> scoreMap;
    MetricStringDistance alg;
    TokenProvider provider;
    String searchWord;

    /**
     * 
     * @param msd        Current String Distance Algorithm to be run
     * @param provider   Token Provider for current corpus
     * @param searchWord Word to search for
     */
    public AlgRunner(MetricStringDistance msd, TokenProvider provider, String searchWord) {
        Levenshtein l = new Levenshtein();
        this.scoreMap = new ConcurrentHashMap<>();
        this.searchWord = searchWord;

    }

    @Override
    public void run() {
        while (provider.hasNextWord()) {
            String cur = provider.getWord();
            Double score = alg.distance(searchWord, cur);
            scoreMap.put(cur, score);

        }
    }

}
