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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;
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
    public static final Color backgroundColor = new Color(70, 73, 75), foregroundColor = new Color(187, 187, 187);

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
        frame.setMinimumSize(new Dimension(750, 600));
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        // use flat look and feel
        // THIS MUST BE DONE UP FRONT OR ELSE IT WILL OVERRIDE ANY CUSTOM CHANGES
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
        
        dropDownHandler.algComboBox.setBackground(backgroundColor);
        dropDownHandler.algComboBox.setForeground(foregroundColor);
        dropDownHandler.getAlgDropDown().setToolTipText("Edit distance algorithm to use");;
        ret.add(dropDownHandler.getAlgDropDown());

        searchWordField = new JTextField("Search Word", FIELD_WIDTH);
        searchWordField.setColumns(FIELD_WIDTH);
        ret.add(searchWordField);
        
        searchWordField.setToolTipText("Word to fuzzy-search for");
        threadCountField = new JSpinner(new SpinnerNumberModel(1, 1, 32, 1));
        JLabel threadNumTextArea = new JLabel("Thread Count: ");
        ret.add(threadNumTextArea);

        threadCountField.setToolTipText("Number of threads to use");
        ret.add(threadCountField);

        dropDownHandler.phoneticComboBox.setBackground(backgroundColor);
        dropDownHandler.phoneticComboBox.setForeground(foregroundColor);
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
        executeButton.setForeground(Color.black);

        JButton clearButton = new JButton("Clear Text");
        clearButton.setBackground(Color.red);
        clearButton.setForeground(Color.black);

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

        dropDownHandler.corpusComboBox.setBackground(backgroundColor);
        dropDownHandler.corpusComboBox.setForeground(foregroundColor);
        dropDownHandler.getCorpusDropDown().setToolTipText("Corpus to search through");
        ret.add(dropDownHandler.getCorpusDropDown());

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
