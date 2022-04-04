package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Iterator;

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

public class BestMatchesPanel {

    static JPanel bestMatchesPanel = null;
    protected static JTextPane bestMatchesPane = null;

    public static JPanel getBestMatchesPanel(){
        if (bestMatchesPanel != null)
            return bestMatchesPanel;
        return createBestMatchesPanel();
    }

    private static JPanel createBestMatchesPanel(){
        bestMatchesPanel = new JPanel();
        bestMatchesPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        bestMatchesPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Best Matches");
        title.setFont(new Font(null, Font.PLAIN, 20));
        bestMatchesPanel.add(title, BorderLayout.PAGE_START);

        bestMatchesPane = new JTextPane();
        bestMatchesPane.setText("Run an algorithm to see the closest words");
        bestMatchesPanel.add(bestMatchesPane, BorderLayout.CENTER);
        return bestMatchesPanel;
    }

    public static void setBestMatchesContents(Iterator iterator){
            StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            ret.append(iterator.next().toString() + "\n");
        }
        bestMatchesPane.setText(ret.toString());
    }
}
