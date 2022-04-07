package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import src.AlgRunner.WordScoreEntry;

public class BestMatchesPanel {

    static JPanel bestMatchesPanel = null;
    protected static JTextPane bestMatchesPane = null;
    static JLabel executionTimeLabel;

    public static JPanel getBestMatchesPanel() {
        if (bestMatchesPanel != null)
            return bestMatchesPanel;
        return createBestMatchesPanel();
    }

    private static JPanel createBestMatchesPanel() {
        bestMatchesPanel = new JPanel();
        bestMatchesPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        bestMatchesPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Best Matches");
        title.setFont(new Font(null, Font.PLAIN, 20));
        bestMatchesPanel.add(title, BorderLayout.PAGE_START);

        bestMatchesPane = new JTextPane();
        bestMatchesPane.setEditable(false);
        JScrollPane scroll = new JScrollPane(bestMatchesPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bestMatchesPanel.add(scroll, BorderLayout.CENTER);
        bestMatchesPane.setText("Run an algorithm to see the closest words");

        // Execution Time
        executionTimeLabel = new JLabel("Algorithm RunTime: N/A");
        bestMatchesPanel.add(executionTimeLabel, BorderLayout.SOUTH);
        return bestMatchesPanel;
    }

    public static void setBestMatchesContents(Iterator<WordScoreEntry> iterator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            WordScoreEntry cur = iterator.next();
            sb.append(String.format("%d:\t%s\t%.2f%n", i + 1, cur.getWord(), cur.getScore()));
        }
        bestMatchesPane.setText(sb.toString());
        return;
    }

    public static void setExecutionTime(long executionTime) {
        executionTimeLabel.setText(String.format("Algorithm RunTime: %d ms", executionTime));
    }
}
