package src;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import info.debatty.java.stringsimilarity.interfaces.*;
import info.debatty.java.stringsimilarity.*;

public class AlgRunner implements Runnable {

    public static class WordScoreEntry implements Comparable<WordScoreEntry> {
        String word;
        Double score;

        public String getWord() {
            return word;
        }

        public Double getScore() {
            return score;
        }

        public WordScoreEntry(String word, Double score) {
            this.word = word;
            this.score = score;
        }

        public String toString() {

            return "Word: " + word + "\t|\tScore: " + score;
        }

        @Override
        public int compareTo(WordScoreEntry o) {
            int scoreCmp = Double.compare(this.score, o.score);
            if (scoreCmp != 0) {
                return scoreCmp;
            } else {
                return this.word.compareTo(o.word);
            }

        }

    }

    public ConcurrentSkipListSet<WordScoreEntry> scoreMap;
    StringDistance alg;
    TokenProvider provider;
    String searchWord;

    /**
     * 
     * @param msd        Current String Distance Algorithm to be run
     * @param provider   Token Provider for current corpus
     * @param searchWord Word to search for
     */
    public AlgRunner(StringDistance msd, TokenProvider provider, String searchWord) {
        this.scoreMap = new ConcurrentSkipListSet<>();
        this.searchWord = searchWord;
        this.provider = provider;
        this.alg = msd;

    }

    @Override
    public void run() {
        while (provider.hasNextWord()) {
            String cur = null;
            while (cur == null) {
                // List is now Empty
                if (!provider.hasNextWord()) {
                    return;
                }
                cur = provider.getWord();
            }
            Double score = alg.distance(searchWord, cur);
            scoreMap.add(new WordScoreEntry(cur, score));

        }
    }

}
