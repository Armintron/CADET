package src.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.DocumentFilter.FilterBypass;

import src.AlgRunner;
import src.AlgRunner.WordScoreEntry;

final class CorpusDocumentFilter extends DocumentFilter {
    private final StyledDocument styledDocument = CorpusTextPanel.getCorpusTextPane()
            .getStyledDocument();
    static private final StyleContext styleContext = StyleContext.getDefaultStyleContext();

    // Define the Different Color Groups
    static protected final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(),
            StyleConstants.Foreground, Color.BLACK);

    // TODO Maybe change where this comes from
    Map<String, Double> wordScoreMap;
    SortedSet<WordScoreEntry> scoreEntries;
    private double intervals;

    public static final AttributeSet[] COLORS = {
            createStyleFromColor(new Color(105, 179, 76)),
            createStyleFromColor(new Color(172, 179, 52)),
            createStyleFromColor(new Color(250, 183, 51)),
            createStyleFromColor(new Color(255, 142, 21)),
            createStyleFromColor(new Color(255, 78, 17)),
            createStyleFromColor(new Color(255, 13, 13)),
    };
    Pattern patterns[];

    public void handleTextChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                updateTextStyles();
            }

        });
    }

    static private AttributeSet createStyleFromColor(Color c) {
        return styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, c);
    }

    private void updateTextStyles() {
        // Clear existing styles
        styledDocument.setCharacterAttributes(0, CorpusTextPanel.getCorpusTextPane().getText().length(),
                blackAttributeSet, true);

        // TODO Maybe we can implement some more concurrency here?
        Matcher matcher = Pattern.compile("\\b\\S*\\b").matcher(CorpusTextPanel.getCorpusTextPane().getText());
        while (matcher.find()) {
            String word;
            try {
                word = styledDocument.getText(matcher.start(), matcher.end() - matcher.start());
            } catch (BadLocationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                continue;
            }
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            // Change Color of found Words=
            try {
                // int index = (int) ((wordScoreMap.get(word) - scoreEntries.first().getScore()) / intervals);
                Double range = scoreEntries.last().getScore() - scoreEntries.first().getScore();
                range = Math.abs(range);
                Double perc = (wordScoreMap.get(word) - scoreEntries.first().getScore()) / range;
                Color myColor = new Color((int)Math.min(255, 512*perc),(int) Math.min(255, 512*(1-perc)), 0);
                styledDocument.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(),
                        createStyleFromColor(myColor), false);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    /**
     * Used to reset the Patterns map holding regex for each word category
     * 
     * @param scores
     * @param abs    The Absolute Values of Z-Score Limit Represented in Map Keys
     * 
     */
    public void resetPatterns(SortedSet<WordScoreEntry> scores) {
        this.scoreEntries = scores;
        wordScoreMap = new HashMap<>();
        double max = scores.last().getScore();
        double min = scores.first().getScore();
        intervals = (max - min) / (COLORS.length - 1);
        ArrayList<WordScoreEntry>[] scoreInvervals = new ArrayList[COLORS.length];
        patterns = new Pattern[COLORS.length];
        for (int i = 0; i < scoreInvervals.length; i++) {
            scoreInvervals[i] = new ArrayList<>();
        }

        for (WordScoreEntry e : scores) {
            int index = (int) ((e.getScore() - min) / intervals);
            scoreInvervals[index].add(e);
            wordScoreMap.put(e.getWord(), e.getScore());
        }

        for (int i = 0; i < patterns.length; i++) {
            patterns[i] = buildPattern(scoreInvervals[i]);
        }

    }

    private Pattern buildPattern(ArrayList<WordScoreEntry> arrayList) {
        StringBuilder sb = new StringBuilder();
        for (WordScoreEntry word : arrayList) {
            sb.append("\\b");
            sb.append(word.getWord());
            sb.append("\\b|");
        }
        // Remove Trailing |
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return Pattern.compile(sb.toString());
    }
}