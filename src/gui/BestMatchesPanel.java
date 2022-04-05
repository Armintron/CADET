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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
        bestMatchesPane.setEditable(false);
        JScrollPane scroll = new JScrollPane(bestMatchesPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                             JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bestMatchesPanel.add(scroll, BorderLayout.CENTER);
        bestMatchesPane.setText("Run an algorithm to see the closest words");
        return bestMatchesPanel;
    }

    public static void setBestMatchesContents(Iterator iterator){
        Document doc = bestMatchesPane.getDocument();
        try{
            doc.remove(0, doc.getLength());
        } catch(BadLocationException e){
            e.printStackTrace();
        }
            for (int i = 0; i < 10; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            try {
                String ins = iterator.next().toString() + System.getProperty("line.separator");
                doc.insertString(doc.getLength(), ins, null);
            } catch(BadLocationException e){
                e.printStackTrace();
            }
        }
    }
}
