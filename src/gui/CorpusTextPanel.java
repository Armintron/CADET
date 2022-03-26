package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class CorpusTextPanel {

    private final static class CorpusDocumentFilter extends DocumentFilter {
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

        Pattern[] patterns;

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            super.insertString(fb, offset, string, attr);
            handleTextChanged();
        }

        private void handleTextChanged() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    updateTextStyles();
                }

            });
        }

        private void updateTextStyles() {
            // Clear existing styles
            styledDocument.setCharacterAttributes(0, getCorpusTextPane().getText().length(), blackAttributeSet, true);

            // TODO Maybe we can implement some more concurrency here?
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(getCorpusTextPane().getText());
                while (matcher.find()) {
                    // Change Color of found Words
                    styledDocument.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(),
                            greenAttributeSet, false);
                }
            }

        }

        /**
         * Used to reset the Patterns [] holding regex for each word category
         * 
         * TODO Implement Method
         */
        private void resetPatterns() {

        }
    }

    static JPanel corpusTextPanel = null;
    protected static JTextPane corpusTextPane = null;
    private static CorpusDocumentFilter corpusDocumentFilter = new CorpusDocumentFilter();

    public static JPanel getCorpusTextPanel() {

        if (corpusTextPanel != null) {
            return corpusTextPanel;
        }
        return createCorpusTextPanel();
    }

    private static JPanel createCorpusTextPanel() {
        corpusTextPanel = new JPanel();
        corpusTextPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // ret.setBackground(Color.cyan);
        corpusTextPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Corpus Text");
        label.setFont(new Font(null, Font.PLAIN, 20));
        corpusTextPanel.add(label, BorderLayout.PAGE_START);
        corpusTextPane = new JTextPane();
        JScrollPane scroll = new JScrollPane(corpusTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        corpusTextPanel.add(scroll, BorderLayout.CENTER);
        setCorpusTextContents(GUI.dropDownHandler.getSelectedCorpus());
        ((AbstractDocument) corpusTextPane.getDocument()).setDocumentFilter(corpusDocumentFilter);
        return corpusTextPanel;
    }

    /**
     * Sets the corpus textArea to contain the empty string ""
     */
    public static void resetCorpusTextPanel() {
        corpusTextPane.setText("");
    }

    /**
     * 
     * @return JTextArea holding current Corpus Text
     */
    public static JTextPane getCorpusTextPane() {
        if (corpusTextPane != null)
            return corpusTextPane;
        createCorpusTextPanel();
        return corpusTextPane;
    }

    /**
     * Used to set Corpus Text Panel contents
     * 
     * @param s String to display on the Corpus Text Panel
     */
    public static void setCorpusTextContents(String s) {
        corpusTextPane.setText(s);
    }

}
