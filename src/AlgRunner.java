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

    public ConcurrentSkipListSet<WordScoreEntry> scoreSet;
    StringDistance alg;
    TokenProvider provider;
    Phonetic phonetic;
    String searchWord;

    /**
     * 
     * @param msd        Current String Distance Algorithm to be run
     * @param provider   Token Provider for current corpus
     * @param searchWord Word to search for
     */
    public AlgRunner(StringDistance msd, TokenProvider provider, String searchWord) {
        this(msd, provider, searchWord, null);
    }

    public AlgRunner(StringDistance msd, TokenProvider provider, String searchWord, Phonetic phonetic)
    {
        Levenshtein l = new Levenshtein();
        this.scoreSet = new ConcurrentSkipListSet<>();
        this.searchWord = searchWord;
        this.provider = provider;
        this.alg = msd;
        this.phonetic = phonetic;
    }

    @Override
    public void run() {
        // cache phonetic representation of the search word if we have an encoder
        String searchPhonetic = "";
        if(phonetic != null) searchPhonetic = phonetic.ToPhonetic(searchWord);
        // our phonetic algorithm isnt useful for this word
        if(searchPhonetic == null) 
        {
            System.out.println("The search word " + searchWord
                    + " has no meaningful phonetic representation with the current encoder: "
                    + phonetic.encoder.toString());
            return;
        }
        
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
                String curPhonetic = phonetic.ToPhonetic(cur);
                // skip over words that dont have a phonetic representation
                if (curPhonetic == null)
                    continue;
                score = alg.distance(searchPhonetic, curPhonetic);
            }
            // find score for words without encoding
            else score = alg.distance(searchWord, cur);
            scoreSet.add(new WordScoreEntry(cur, score));

        }
    }

}