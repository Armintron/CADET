package src.gui;

import javax.swing.*;

import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import src.corpi.CorpusProvider;

import java.awt.*;
import java.net.URL;

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
    protected final JComboBox algComboBox = new JComboBox<>(ALG_OPTIONS);
    protected final JComboBox corpusComboBox = new JComboBox<>(CORPI_OPTIONS);

    public DropDownHandler() {

        corpusComboBox.addActionListener((e) -> {
            int selected = algComboBox.getSelectedIndex();
            GUI.setCorpusTextPanel(CorpusProvider.getCorpus(selected));

        });
    }

    /**
     * 
     * @return Dropdown menu for Algorithm Options
     */
    public JComboBox getAlgDropDown() {
        return algComboBox;

    }

    /**
     * 
     * @return Dropdown menu for Corpus Options
     */
    public JComboBox getCorpusDropDown() {
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
