package src.gui;

import java.util.Arrays;

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

import src.Phonetic.Encoder;

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

    private final static String[] OPTIONAL_PHONECTIC_ENCODER = {
            "NONE",
            "CaverPhone2",
            "DoubleMetaphone",
            "MatchRating",
            "Metaphone",
            "Nysiis",
            "RefinedSoundex",
            "Soundex"
    };
    public static String[] CORPI_OPTIONS = { "All Words", "Bee Movie", "Dracula", "Edgar Allan Poe", "Frankenstein",
            "Mobydick", "Winnne the Pooh" };
    protected final JComboBox<String> algComboBox = new JComboBox<>(ALG_OPTIONS);
    protected final JComboBox<String> corpusComboBox = new JComboBox<>(CORPI_OPTIONS);
    protected final JComboBox<String> phoneticComboBox = new JComboBox<String>(OPTIONAL_PHONECTIC_ENCODER);

    public DropDownHandler() {
        corpusComboBox.addActionListener((e) -> {
            CorpusTextPanel.setCorpusTextContents(CorpusProvider.getCorpus(corpusComboBox.getSelectedIndex()));

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

    public JComboBox<String> getEncoderDropDown() {
        return phoneticComboBox;
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

    /**
     * 
     * @return Encoder if selected, else null
     */
    public Encoder getSelectedPhonecticEncoder() {
        String selection = (String) phoneticComboBox.getSelectedItem();
        if (selection == null || selection.equals("NONE"))
            return null;
        return Encoder.valueOf(selection);
    }

}
