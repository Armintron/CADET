package src.gui;

import javax.swing.*;

import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

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
    protected JComboBox algComboBox = null;
    protected JComboBox corpusComboBox = null;

    public DropDownHandler() {

        this.corpusComboBox = new JComboBox<String>(CORPI_OPTIONS);
        this.algComboBox = new JComboBox<String>(ALG_OPTIONS);
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

}
