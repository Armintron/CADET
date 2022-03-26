package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import src.corpi.CorpusProvider;

public class GUI {

    // Class Constants
    private static final int WINDOW_WIDTH = 750;// pixels
    private static final int WINDOW_HEIGHT = 735;// pixels
    private static final int FIELD_WIDTH = 35;// characters
    private static final int AREA_WIDTH = 40;// characters
    private static final int BUTTON_HORIZONTAL_SPACING = 10;
    private static final int BUTTON_VERTICAL_SPACING = 10;
    private String APPLICATION_NAME = "CADET Fuzzy String Search";
    JFrame MAIN_WINDOW;

    // Custom Class Instances
    protected static DropDownHandler connectionPanel;
    private static JTextArea corpusTextArea;
    private static JTextField searchWordField;
    protected static DropDownHandler dropDownHandler = new DropDownHandler();

    public GUI() {

        // Init window
        JFrame frame = new JFrame(APPLICATION_NAME);
        MAIN_WINDOW = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(650, 600));
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        // Bottom Border UI
        frame.add(bottomUI(), BorderLayout.SOUTH);

        // Center Text Box
        frame.add(createCorpusTextPanel(), BorderLayout.CENTER);
        frame.add(createTopUI(), BorderLayout.NORTH);
        // frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // x and y

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // x and y
        frame.setVisible(true);

    }

    private static JPanel createTopUI() {
        JPanel ret = new JPanel();
        ret.setLayout(new FlowLayout(FlowLayout.CENTER, BUTTON_HORIZONTAL_SPACING, BUTTON_VERTICAL_SPACING));
        searchWordField = new JTextField("Search Word", FIELD_WIDTH);
        searchWordField.setColumns(FIELD_WIDTH);
        ret.add(dropDownHandler.getAlgDropDown());
        ret.add(searchWordField);
        return ret;
    }

    /**
     * Sets the corpus textArea to contain the empty string ""
     */
    public static void resetCorpusTextPanel() {
        corpusTextArea.setText("");
    }

    /**
     * Used to set Corpus Text Panel contents
     * 
     * @param s String to display on the Corpus Text Panel
     */
    public static void setCorpusTextPanel(String s) {
        corpusTextArea.setText(s);
    }

    private static JPanel bottomUI() {
        JPanel ret = new JPanel();
        ret.setLayout(new FlowLayout(FlowLayout.CENTER, BUTTON_HORIZONTAL_SPACING, BUTTON_VERTICAL_SPACING));

        // Execute and Clear Command Buttons
        JButton executeButton = new JButton("Execute Search");
        executeButton.setBackground(Color.green);
        JButton clearButton = new JButton("Clear Text");
        clearButton.setBackground(Color.red);
        // Set up clear action
        clearButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.resetCorpusTextPanel();

            }

        });

        ret.add(dropDownHandler.getCorpusDropDown());
        ret.add(executeButton);
        ret.add(clearButton);

        return ret;
    }

    public JPanel createCorpusTextPanel() {
        JPanel ret = new JPanel();
        ret.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // ret.setBackground(Color.cyan);
        ret.setLayout(new BorderLayout());
        JLabel label = new JLabel("Corpus Text");
        label.setFont(new Font(null, Font.PLAIN, 20));
        ret.add(label, BorderLayout.PAGE_START);
        JTextArea inputArea = new JTextArea();
        this.corpusTextArea = inputArea;
        JScrollPane scroll = new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ret.add(scroll, BorderLayout.CENTER);
        setCorpusTextPanel(dropDownHandler.getSelectedCorpus());

        return ret;
    }

    public static void main(String[] args) {
        GUI gui = new GUI();

    }
}
