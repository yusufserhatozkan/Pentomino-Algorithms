package Phase3;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StartingPage extends JFrame {
    private JComboBox<String> algoComboBox;
    private JTextField inputField1; // input field for L
    private JTextField inputField2; // input field for T
    private JTextField inputField3; // input field for P
    private static int intL = -1; // integer user input for L
    private static int intT = -1; // integer user input for T
    private static int intP = -1; // integer user input for P

    public StartingPage() {
        setTitle("Knapsack AlgÏorithm Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridLayout(4, 1)); // Changed to 4 rows to accommodate the new JTextField

        // Styling fonts
        Font titleFont = new Font("Monospaced", Font.BOLD, 30);
        Font comboFont = new Font("Arial", Font.PLAIN, 22);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Font labelFont = new Font("Monospaced", Font.BOLD, 40);

        // Title Panel
        JLabel title = new JLabel("KNAPSACK", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        add(title);

        // Algorithm Selection ComboBox
        algoComboBox = new JComboBox<>();
        algoComboBox.setUI(new StyledComboBoxUI());
        algoComboBox.setFont(comboFont);
        algoComboBox.setForeground(Color.WHITE);
        algoComboBox.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        algoComboBox.addItem("  Dancing Links");
        algoComboBox.addItem("  Generic Algorithm");
        algoComboBox.addItem("  Greedy Algorithm");
        algoComboBox.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        add(algoComboBox);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        inputPanel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns, with gaps
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set a white line border

        // Labels
        JLabel labelLs = new JLabel("L", SwingConstants.CENTER);
        labelLs.setForeground(Color.WHITE);
        labelLs.setFont(labelFont);
        inputPanel.add(labelLs);

        JLabel labelTs = new JLabel("T", SwingConstants.CENTER);
        labelTs.setForeground(Color.WHITE);
        labelTs.setFont(labelFont);
        inputPanel.add(labelTs);

        JLabel labelPs = new JLabel("P", SwingConstants.CENTER);
        labelPs.setForeground(Color.WHITE);
        labelPs.setFont(labelFont);
        inputPanel.add(labelPs);

        // Text Fields
        inputField1 = new JTextField(10);
        inputField2 = new JTextField(10);
        inputField3 = new JTextField(10);
        inputPanel.add(createInputSubPanel(inputField1, labelFont));
        inputPanel.add(createInputSubPanel(inputField2, labelFont));
        inputPanel.add(createInputSubPanel(inputField3, labelFont));

        add(inputPanel);

        // Proceed Button
        JButton button = new JButton("PROCEED");
        button.setFont(buttonFont);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 30, 30)); // Dark gray
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        add(button);

        // Listener for the button
        ChoiceLabelListener listener = new ChoiceLabelListener();
        button.addActionListener(listener);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createInputSubPanel(JTextField textField, Font font) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        textField.setFont(font);
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        textField.setCaretColor(Color.BLACK);
        textField.setHorizontalAlignment(JTextField.CENTER); // Center text
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Ensures that the text field is centered within the panel
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));

        panel.add(textField);

        return panel;
    }

    class ChoiceLabelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String algorithmName = (String) algoComboBox.getSelectedItem();
            try {
                StartingPage.intL = Integer.parseInt(inputField1.getText().trim());
                StartingPage.intT = Integer.parseInt(inputField2.getText().trim());
                StartingPage.intP = Integer.parseInt(inputField3.getText().trim());

                // Check for negative values
                if (StartingPage.intL < 0 || StartingPage.intT < 0 || StartingPage.intP < 0) {
                    throw new IllegalArgumentException("Negative values are not allowed.");
                }
                System.out.println("L value: " + intL);
                System.out.println("T value: " + intT);
                System.out.println("P value: " + intP);

                System.out.println(algorithmName);

                if (algorithmName.equalsIgnoreCase("  Dancing Links")) {
                    System.out.println("TRUE");
                    // Create and display the UI3 frame
                    new Thread(() -> {
                        CargoSpaceVisualizer.main(null);
                    }).start();

                    // Close the current StartingPage3 frame
                    setVisible(false);
                    dispose();
                }

                if (algorithmName.equalsIgnoreCase("  Generic Algorithm")) {
                    System.out.println("TRUE");
                    // Create and display the UI3 frame
                    new Thread(() -> {
                        CargoSpaceVisualizer.main(null);
                    }).start();

                    // Close the current StartingPage3 frame
                    setVisible(false);
                    dispose();
                }

                if (algorithmName.equalsIgnoreCase("  Greedy Algorithm")) {
                    System.out.println("TRUE");
                    // Create and display the CargoSpaceVisualizer JavaFX frame
                    new Thread(() -> {
                        CargoSpaceVisualizer.main(null);

                    }).start();

                    // Close the current StartingPage3 frame
                    setVisible(false);
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(StartingPage.this,
                        "Please enter valid numbers",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(StartingPage.this,
                        ex.getMessage(),
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Custom UI for JComboBox
    class StyledComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton("•••");
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(30, 30, 30));
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            return button;
        }
    }

    
    
    public static boolean possibleSolutionPent (int lInput , int pInput , int tInput){
        if (5 * (lInput + pInput + tInput) <= 1320){
            return true;
        }else return false; 
    }
    
    public static boolean possibleSolutionParc (int aInput , int bInput , int cInput){
        if ( (aInput * 16) + (bInput * 24) + (cInput * 27 )<= 1320){
            return true;
        }else return false; 
    }



    public static int getNumL() {
        return intL;
    }

    public static int getNumT() {
        return intT;
    }

    public static int getNumP() {
        return intP;
    }

    public static int getNumA() {
        return intL;
    }

    public static int getNumB() {
        return intT;
    }

    public static int getNumC() {
        return intP;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartingPage());
    }
}
