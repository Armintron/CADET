package src.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import src.AlgRunner.WordScoreEntry;
import src.ResultStats;

final class CorpusDocumentFilter extends DocumentFilter {
    private final StyledDocument styledDocument = CorpusTextPanel.getCorpusTextPane()
            .getStyledDocument();
    static private final StyleContext styleContext = StyleContext.getDefaultStyleContext();

    // Define the Different Color Groups
    static protected final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(),
            StyleConstants.Foreground, Color.BLACK);

    // TODO Maybe change where this comes from
    ResultStats stats = null;

    public static final AttributeSet[] COLORS = {
            createStyleFromColor(new Color(105, 179, 76)),
            createStyleFromColor(new Color(172, 179, 52)),
            createStyleFromColor(new Color(250, 183, 51)),
            createStyleFromColor(new Color(255, 142, 21)),
            createStyleFromColor(new Color(255, 78, 17)),
            createStyleFromColor(new Color(255, 13, 13)),
    };
    Pattern patterns[];

    /**
     * Handles String Distance HeatMap Coloring
     */
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

        // Maybe we can implement some more concurrency here?
        Matcher matcher = Pattern.compile("\\b\\S*\\b").matcher(CorpusTextPanel.getCorpusTextPane().getText());
        while (matcher.find()) {
            String word;
            try {
                word = styledDocument.getText(matcher.start(), matcher.end() - matcher.start());
            } catch (BadLocationException e) {
                e.printStackTrace();
                continue;
            }
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            // Change Color of found Words
            try {
                double score = stats.scoreMap.get(word);
                Color curColor = new Color((int) Math.min(255, 512 * score), (int) Math.min(255, 512 * (1 - score)), 0);
                styledDocument.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(),
                        createStyleFromColor(curColor), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    /**
     * 
     * @param stats stats from current String Distance Algorithm run
     */
    public void setResultStats(ResultStats stats) {
        this.stats = stats;
    }
}