package Phase2;

/**
 * @author Group 8 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingPage {
    // defined constants for colors and fonts used in the UI
    private static final Color backgroundColor = new Color(46, 64, 83); // dark background color
    private static final Color borderColor = new Color(255, 215, 0); // gold/yellow border
    private static final Color textColor = new Color(255, 215, 0); // gold/yellow text
    private static final Font titleFont = new Font("Arial", Font.BOLD, 34); // tite at the top
    private static final Font buttonFont = new Font("Arial", Font.BOLD, 24); // text in button

    public static void main(String[] args) {
        System.out.println("LOADING THE GAME STARTING PAGE...");

        // use SwingUtilities.invokeLater so that the GUI is created in the
        // Event-Dispatching Thread
        SwingUtilities.invokeLater(() -> {

            // ************************* MAIN FRAME BELOW ******************************

            JFrame frame = new JFrame("GROUP 8 TETRIS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null); // center the frame
            frame.setResizable(false); // no resizing of the frame
            frame.getContentPane().setBackground(backgroundColor); // set background color

            // ************************* MAIN FRAME ABOVE ******************************

            // ************************* MAIN PANEL BELOW ******************************

            // main panel with padding
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(backgroundColor);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // add padding around the panel
            frame.add(mainPanel);

            // ************************* MAIN PANEL ABOVE ******************************

            // ************************* TITLE PANEL BELOW ******************************

            // title panel with padding
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            titlePanel.setBackground(backgroundColor);
            titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // add padding around the panel

            JLabel titleLabel = new JLabel("GROUP 8 TETRIS");
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // center align the label
            titleLabel.setFont(titleFont); // set font
            titleLabel.setForeground(textColor); // set text color
            titlePanel.add(titleLabel);
            mainPanel.add(titlePanel);

            // ************************* TITLE PANEL ABOVE ******************************

            // ************************* BUTTONS BELOW ******************************

            // panel for buttons with padding and grid layout
            JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 20, 20));
            buttonPanel.setBackground(backgroundColor);
            mainPanel.add(buttonPanel);

            // add human button to the button panel
            JButton humanPlayerButton = createButton("Human Player");
            humanPlayerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false); // hide the main frame
                    frame.dispose(); // dispose the frame
                    new Thread(() -> {
                        HumanGame humanGame = new HumanGame();
                        humanGame.initGame(); // start the human game in a new thread
                    }).start();
                }
            });
            buttonPanel.add(humanPlayerButton);

            // add bot button to the button panel
            JButton botButton = createButton("Bot");
            botButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false); // hide the main frame
                    frame.dispose(); // dispose the frame

                    // start the bot game in a new thread
                    new Thread(() -> {
                        Bot botGame = new Bot();
                        botGame.initGame(); // start the bot game in a new thread
                    }).start();
                }
            });
            buttonPanel.add(botButton);

            // add bot movement button to the button panel
            JButton botMovementButton = createButton("Bot With Movement");
            botMovementButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false); // hide the main frame
                    frame.dispose(); // dispose the frame

                    // start the bot movement game in a new thread
                    new Thread(() -> {
                        BotMovement botMovementGame = new BotMovement();
                        botMovementGame.initGame(); // start the bot game in a new thread
                    }).start();
                }
            });
            buttonPanel.add(botMovementButton);

            // add best sequence button to the button panel
            JButton bestSequenceButton = createButton("Best Sequence");
            bestSequenceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false); // hide the main frame
                    frame.dispose(); // dispose the frame

                    // Start the best sequence game in a new thread
                    new Thread(() -> {
                        BestSequence bestSequenceGame = new BestSequence();
                        bestSequenceGame.initGame(); // start the best sequence game in a new thread
                    }).start();
                }
            });
            buttonPanel.add(bestSequenceButton);

            // add exit to the button panel
            JButton exitButton = createButton("Exit");
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("EXITING THE GAME PAGE...");
                    System.exit(0); // exit
                }
            });
            buttonPanel.add(exitButton);
            // ************************* BUTTONS ABOVE ******************************

            frame.setVisible(true); // set the frame visible
            // this includes the mainPanel, which includes titlePanel and buttonPanel
        });
    }

    /**
     * the buttons you see at the starting page use this method to look idential and
     * to reuse code.
     * 
     * @param title the text inside the StartingPage button
     * @return the JButton with different text
     */
    private static JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(200, 40)); // preferred size
        button.setMaximumSize(new Dimension(200, 40)); // preferred size
        button.setBackground(backgroundColor); // set button background
        button.setForeground(borderColor); // set text color
        button.setFont(buttonFont); // set font
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 4), // outer border
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // padding inside the button
        ));
        return button;
    }
}