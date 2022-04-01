package src.gui;

import javax.swing.JComboBox;

import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.MetricLCS;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.OptimalStringAlignment;
import info.debatty.java.stringsimilarity.QGram;
import info.debatty.java.stringsimilarity.RatcliffObershelp;
import info.debatty.java.stringsimilarity.SorensenDice;
import info.debatty.java.stringsimilarity.WeightedLevenshtein;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import src.corpi.CorpusProvider;

public class DropDownHandler {

    public final static String[] ALG_OPTIONS = {
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
            "SorensenDice",
    };
    public final static StringDistance[] ALGS = {
            new Cosine(),
            new Damerau(),
            new Jaccard(),
            new JaroWinkler(),
            new Levenshtein(),
            new LongestCommonSubsequence(),
            new MetricLCS(),
            new NGram(),
            new NormalizedLevenshtein(),
            new OptimalStringAlignment(),
            new QGram(),
            new RatcliffObershelp(),
            new SorensenDice()
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
        return ALGS[index];

    }

    /**
     * 
     * @return String representation of selected Corpus
     */
    public String getSelectedCorpus() {
        return CorpusProvider.getCorpus(corpusComboBox.getSelectedIndex());
    }

}
