package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class GUI {

    // Class Constants
    private static final int WINDOW_WIDTH = 750;// pixels
    private static final int WINDOW_HEIGHT = 735;// pixels
    private static final int FIELD_WIDTH = 15;// characters
    private static final int AREA_WIDTH = 40;// characters
    private static final int BUTTON_HORIZONTAL_SPACING = 10;
    private static final int BUTTON_VERTICAL_SPACING = 10;
    private String APPLICATION_NAME = "CADET Fuzzy String Search";
    JFrame MAIN_WINDOW;

    // Custom Class Instances
    protected static DropDownHandler connectionPanel;
    protected static JTextField searchWordField;
    protected static JSpinner threadCountField;
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
        // frame.add(BestMatchesPanel.getBestMatchesPanel(), BorderLayout.CENTER);

        // Center Text Box
        // frame.add(CorpusTextPanel.getCorpusTextPanel(), BorderLayout.CENTER);
        frame.add(middleUI(), BorderLayout.CENTER);
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
        dropDownHandler.getAlgDropDown().setToolTipText("Edit distance algorithm to use");;
        ret.add(searchWordField);
        searchWordField.setToolTipText("Word to fuzzy-search for");
        threadCountField = new JSpinner(new SpinnerNumberModel(1, 1, 32, 1));
        threadCountField.setToolTipText("Number of threads to use");
        ret.add(threadCountField);
        dropDownHandler.phoneticComboBox.setToolTipText("Phonetic encoder to use (optional)");
        ret.add(dropDownHandler.phoneticComboBox);
        return ret;
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
                CorpusTextPanel.resetCorpusTextPanel();

            }

        });

        // Set Up Execute Action
        executeButton.addActionListener((e) -> {
            CorpusTextPanel.executeCorpusSearch();
        });

        ret.add(dropDownHandler.getCorpusDropDown());
        dropDownHandler.getCorpusDropDown().setToolTipText("Corpus to search through");
        ret.add(executeButton);
        ret.add(clearButton);

        return ret;
    }

    private static JPanel middleUI() {
        JPanel ret = new JPanel();
        ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));

        ret.add(CorpusTextPanel.getCorpusTextPanel());
        ret.add(BestMatchesPanel.getBestMatchesPanel());

        return ret;
    }

    public static void main(String[] args) {
        GUI gui = new GUI();

    }
}
