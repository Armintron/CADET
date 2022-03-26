package src.gui;

import javax.swing.JComboBox;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import src.corpi.CorpusProvider;

public class DropDownHandler {

    public static String[] ALG_OPTIONS = {
            "Cosine",
            "Damerau",
            "Jaccard",
            "JaroWinkler",
            "Levenshtein",
            "LongestCommonSubsequence",
            "MetricLCS",
            "NGram",
            "NormalizedLevenshtein",
            "OptimalStringAlignment",
            "QGram",
            "RatcliffObershelp",
            "ShingleBased",
            "SorensenDice",
            "WeightedLevenshtein",
    };
    public static String[] CORPI_OPTIONS = { "Bee Movie" };
    protected final JComboBox<String> algComboBox = new JComboBox<>(ALG_OPTIONS);
    protected final JComboBox<String> corpusComboBox = new JComboBox<>(CORPI_OPTIONS);

    public DropDownHandler() {

        corpusComboBox.addActionListener((e) -> {
            int selected = algComboBox.getSelectedIndex();
            CorpusTextPanel.setCorpusTextContents(CorpusProvider.getCorpus(selected));

        });
    }

    /**
     * 
     * @return Dropdown menu for Algorithm Options
     */
    public JComboBox<String> getAlgDropDown() {
        return algComboBox;

    }

    /**
     * 
     * @return Dropdown menu for Corpus Options
     */
    public JComboBox<String> getCorpusDropDown() {
        return corpusComboBox;
    }

    /**
     * 
     * @return String Metric Algorithm for selected option on AlgDropDown
     */
    public StringDistance getSelectedAlg() {
        int index = algComboBox.getSelectedIndex();
        return null;

    }

    /**
     * 
     * @return String representation of selected Corpus
     */
    public String getSelectedCorpus() {
        return CorpusProvider.getCorpus(corpusComboBox.getSelectedIndex());
    }

}
