package src;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * Token Provider classs which provides single word tokens from a single
 * corpus
 * All words will be transformed to only contain letters. All words will be
 * set to lower case.
 * 
 * 
 */
public class TokenProvider {

    public String corpusName;
    private List<String> words;
    Iterator<String> wordIterator;
    ConcurrentLinkedQueue<String> wordQueue;

    /**
     * 
     * 
     * @param corpus File containing search space corpus
     */
    public TokenProvider(File corpus) {
        try (Scanner input = new Scanner(corpus)) {
            ArrayList<String> words = new ArrayList<>();
            while (input.hasNext()) {
                String cur = input.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
                words.add(cur);
            }
            input.close();
            this.words = words;
            this.wordQueue = new ConcurrentLinkedQueue<>(words);
            this.corpusName = corpus.getName();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param corpus String containing corpus
     */
    public TokenProvider(String corpus) {
        this.words = new ArrayList<>();
        for (String word : corpus.split(" |\n")) {
            String cur = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            words.add(cur);
        }
        this.wordQueue = new ConcurrentLinkedQueue<>(words);

    }

    /**
     * 
     * @return the next word in the wordQueue ConcurrentLinkedQueue
     */
    public String getWord() {
        return this.wordQueue.poll();
    }

    public boolean hasNextWord() {
        return !this.wordQueue.isEmpty();
    }

    /**
     * Resets the ConcurrentLinkedQueue held in wordQueue
     * 
     * @return Instance of ConcurrentLinkedQueue held in wordQueue
     */
    public ConcurrentLinkedQueue<String> restartIterator() {
        this.wordQueue = new ConcurrentLinkedQueue<>(this.words);
        return this.wordQueue;
    }

}
