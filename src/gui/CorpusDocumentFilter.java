package src.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

import src.AlgRunner.WordScoreEntry;

final class CorpusDocumentFilter extends DocumentFilter {
    private final StyledDocument styledDocument = CorpusTextPanel.getCorpusTextPane()
            .getStyledDocument();
    private final StyleContext styleContext = StyleContext.getDefaultStyleContext();

    // Define the Different Color Groups
    private final AttributeSet greenAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(),
            StyleConstants.Foreground, Color.GREEN);
    private final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(),
            StyleConstants.Foreground, Color.BLACK);
    private final AttributeSet redAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(),
            StyleConstants.Foreground, Color.RED);

    Map<Integer, Pattern> patterns = new HashMap<>();

    public void handleTextChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                updateTextStyles();
            }

        });
    }

    private void updateTextStyles() {
        // Clear existing styles
        styledDocument.setCharacterAttributes(0, CorpusTextPanel.getCorpusTextPane().getText().length(),
                blackAttributeSet, true);

        // TODO Maybe we can implement some more concurrency here?
        Pattern pattern = patterns.get(-1);
        Matcher matcher = pattern.matcher(CorpusTextPanel.getCorpusTextPane().getText());
        while (matcher.find()) {
            // Change Color of found Words
            styledDocument.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(),
                    greenAttributeSet, false);
        }

    }

    /**
     * Used to reset the Patterns [] holding regex for each word category
     * 
     * TODO Implement Method
     * 
     * @param scores
     * @param abs    The Absolute Values of Z-Score Limit Represented in Map Keys
     * 
     */
    public void resetPatterns(Map<Integer, ArrayList<WordScoreEntry>> scores, int abs) {

        for (int i = -1 * abs; i <= abs; i++) {
            patterns.put(i, buildPattern(scores.get(i)));
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