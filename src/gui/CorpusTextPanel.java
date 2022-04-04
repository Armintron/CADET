package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import src.AlgRunner;
import src.Main;
import src.ResultStats;
import src.TokenProvider;
import src.AlgRunner.WordScoreEntry;

public class CorpusTextPanel {

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

    public static void executeCorpusSearch() {
        String input = GUI.searchWordField.getText();
        StringDistance alg = GUI.dropDownHandler.getSelectedAlg();
        TokenProvider tp = new TokenProvider(corpusTextPane.getText());
        AlgRunner runner = new AlgRunner(alg, tp, input);
        Main.startAndWaitForThreads(runner, GUI.NUM_THREADS);
        ResultStats stats = new ResultStats(runner.scoreMap);

        corpusDocumentFilter.setResultStats(stats);
        corpusDocumentFilter.handleTextChanged();
        BestMatchesPanel.setBestMatchesContents(runner.scoreMap.iterator());
    }
}
