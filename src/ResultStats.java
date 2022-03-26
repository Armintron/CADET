package src;

import java.util.Set;

import src.AlgRunner.WordScoreEntry;

public class ResultStats {

    Set<WordScoreEntry> scores;
    double mean, stdDev;

    public ResultStats(Set<WordScoreEntry> scoreMap) {
        scores = scoreMap;
        computeStats();
    }

    public double getZScore(double x) {
        return (x - mean) / stdDev;
    }

    private void computeStats() {

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

}
