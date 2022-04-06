package src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import src.AlgRunner.WordScoreEntry;

public class ResultStats {

    public SortedSet<WordScoreEntry> scores;
    public HashMap<String, Double> scoreMap = new HashMap<>();
    public double mean;
    public Double stdDev = null;
    public double max, min;

    public ResultStats(SortedSet<WordScoreEntry> scoreMap) {
        scores = scoreMap;
        max = scoreMap.last().score;
        min = scoreMap.first().score;
        computeStats();
    }

    public double getZScore(double x) {
        if (stdDev == null)
            computeStandardDeviation();
        return (x - mean) / stdDev;
    }

    private void computeStandardDeviation() {
        // TODO Much Optimization can be done here!!
        // Get Mean
        long total = 0;
        for (AlgRunner.WordScoreEntry w : scores) {
            total += w.getScore();
        }
        this.mean = (total / scores.size());

        // get Std. Dev
        long numerator = 0;
        for (AlgRunner.WordScoreEntry w : scores) {
            numerator += w.getScore() - mean;
        }
        this.stdDev = Math.sqrt(numerator / (scores.size() - 1));
    }

    private void computeStats() {

        for (WordScoreEntry wse : scores) {
            wse.score = (wse.score - min) / (max - min);
            scoreMap.put(wse.word, wse.score);
        }

    }

}
