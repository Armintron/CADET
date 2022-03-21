package src;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import info.debatty.java.stringsimilarity.interfaces.*;
import info.debatty.java.stringsimilarity.*;

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
            int scoreCmp = (int) (this.score - o.score);
            if (scoreCmp != 0) {
                return scoreCmp;
            } else {
                return this.word.compareTo(o.word);
            }

        }

    }

    ConcurrentSkipListSet<WordScoreEntry> scoreMap;
    MetricStringDistance alg;
    TokenProvider provider;
    Phonetic phonetic;
    String searchWord;

    /**
     * 
     * @param msd        Current String Distance Algorithm to be run
     * @param provider   Token Provider for current corpus
     * @param searchWord Word to search for
     */
    public AlgRunner(MetricStringDistance msd, TokenProvider provider, String searchWord) {
        this(msd, provider, searchWord, null);
    }

    public AlgRunner(MetricStringDistance msd, TokenProvider provider, String searchWord, Phonetic phonetic)
    {
        Levenshtein l = new Levenshtein();
        this.scoreMap = new ConcurrentSkipListSet<>();
        this.searchWord = searchWord;
        this.provider = provider;
        this.alg = msd;
        this.phonetic = phonetic;
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
            Double score;
            // encode words and then find score
            if(phonetic != null)
            {
                score = alg.distance(phonetic.ToPhonetic(searchWord), phonetic.ToPhonetic(cur));
            }
            // find score for words without encoding
            else score = alg.distance(searchWord, cur);
            scoreMap.add(new WordScoreEntry(cur, score));

        }
    }

}
