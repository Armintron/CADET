package src;

import java.util.concurrent.ConcurrentSkipListSet;

import info.debatty.java.stringsimilarity.interfaces.*;

public class AlgRunner implements Runnable {

    static class WordScoreEntry implements Comparable<WordScoreEntry> {
        String word;
        Double score;

        public WordScoreEntry(String word, Double score) {
            this.word = word;
            this.score = score;
        }

        public String toString() {

            return "Word: " + word + "\t|\tScore: " + score;
        }

        @Override
        public int compareTo(WordScoreEntry o) {
            double scoreCmp = (this.score - o.score);
            if (scoreCmp > 0) {
                return 1;
            } else if (scoreCmp < 0) {
                return -1;
            } else {
                return 0;
            }

        }

    }

    ConcurrentSkipListSet<WordScoreEntry> scoreMap;
    MetricStringDistance msd;
    NormalizedStringDistance nsd;
    TokenProvider provider;
    String searchWord;

    /**
     * 
     * @param msd        Current String Distance Algorithm to be run
     * @param provider   Token Provider for current corpus
     * @param searchWord Word to search for
     */
    public AlgRunner(MetricStringDistance msd, TokenProvider provider, String searchWord) {
        this.scoreMap = new ConcurrentSkipListSet<>();
        this.searchWord = searchWord;
        this.provider = provider;
        this.msd = msd;

    }

    public AlgRunner(NormalizedStringDistance nsd, TokenProvider provider, String searchWord) {
        this.scoreMap = new ConcurrentSkipListSet<>();
        this.searchWord = searchWord;
        this.provider = provider;
        this.nsd = nsd;

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

            if (nsd != null) {
                Double score = nsd.distance(searchWord, cur);
                scoreMap.add(new WordScoreEntry(cur, score));
            } else {
                Double score = msd.distance(searchWord, cur);
                scoreMap.add(new WordScoreEntry(cur, score));
            }
        }
    }

}
