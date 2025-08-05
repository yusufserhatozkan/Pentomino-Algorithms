package Phase2;

/**
 * @author Group 8 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPicture {
    // defined constants for colors and fonts used in the UI
    private static final Color backgroundColor = new Color(46, 64, 83); // dark blue background
    private static final Color borderColor = new Color(255, 215, 0); // gold/yellow border
    private static final Color textColor = new Color(255, 215, 0); // gold/yellow text
    private static final Font titleFont = new Font("Arial", Font.BOLD, 34); // font for titles
    private static final Font scoreFont = new Font("Arial", Font.BOLD, 20); // font for scores
    private static final Font buttonFont = new Font("Arial", Font.BOLD, 20); // font for buttons

    // **** REUSABLE CODE FOR GAME OVER POP UP BELOW ****

    /**
     * method to create and display the game over popup window for the 3 individual
     * games: human game, bot, best sequence with their own title, score, and button
     * actions.
     * 
     * @param title       the title of the popup window, "Game Over"
     * @param scoreText   the text to display the score information
     * @param onPlayAgain the action to perform when the "PLAY AGAIN" button is
     *                    clicked
     */
    private static void createGameOverPopUp(String title, String scoreText, Runnable onPlayAgain) {
        JFrame frame = new JFrame("Game Over"); // main window for the popup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // window size
        frame.setLocationRelativeTo(null); // center window on screen
        frame.setResizable(false); // disable window resizing
        frame.getContentPane().setBackground(backgroundColor); // set background color

        // Setup and add main panel to the frame
        JPanel mainPanel = setupMainPanel();
        frame.add(mainPanel);

        // Add title, score, and buttons to the main panel
        mainPanel.add(createLabel(title, titleFont), BorderLayout.NORTH); // add title label
        mainPanel.add(createLabel(scoreText, scoreFont), BorderLayout.CENTER); // add score label
        mainPanel.add(createButtonPanel(frame, onPlayAgain), BorderLayout.SOUTH); // add button panel

        frame.setVisible(true); // Make the frame visible
    }

    /**
     * sets up the main panel with BorderLayout
     * this main panel adds the Label and Button panel in the createGameOverPopUp
     * 
     * @return JPanel - the main panel
     */
    private static JPanel setupMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout()); // use BorderLayout for layout
        mainPanel.setBackground(backgroundColor); // set the background color
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // add padding around the panel
        return mainPanel;
    }

    /**
     * create a label with specified text and font
     * this label is added to the mainPanel for the title and score
     * 
     * @param text the text to be displayed in the label
     * @param font the font to be used for the label's text
     * @return JLabel - the created label
     */
    private static JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER); // center-align the text
        label.setForeground(textColor); // set text color
        label.setFont(font); // set font
        return label;
    }

    /**
     * create a panel with buttons for different actions
     * This panel includes "PLAY AGAIN", "MENU", and "EXIT" buttons, each with
     * specific actions.
     * 
     * 
     * @param frame       the main JFrame of the popup
     * @param onPlayAgain a Runnable action that defines what happens when "PLAY
     *                    AGAIN" is clicked
     * @return JPanel - the panel containing the buttons
     */
    private static JPanel createButtonPanel(JFrame frame, Runnable onPlayAgain) {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 20)); // gridLayout for buttons
        buttonPanel.setBackground(backgroundColor); // set background color

        // create and add "PLAY AGAIN" button
        JButton playButton = createButton("PLAY AGAIN");
        playButton.addActionListener(e -> {
            frame.setVisible(false); // hide the frame
            frame.dispose(); // dispose the frame
            onPlayAgain.run(); // execute the action for "PLAY AGAIN"
        });
        buttonPanel.add(playButton);

        // create and add "MENU" button
        JButton menuButton = createButton("MENU");
        menuButton.addActionListener(e -> {
            frame.setVisible(false); // hide the frame
            frame.dispose(); // dispose the frame
            StartingPage.main(null); // ppen the starting page
        });
        buttonPanel.add(menuButton);

        // create and add "EXIT" button
        JButton exitButton = createButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0)); // exit the application
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    /**
     * create a styled JButton button with specified title
     * 
     * @param title the text to be displayed on the button
     * @return JButton - the styled button with the specified title
     */
    private static JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setBackground(backgroundColor); // set background color
        button.setForeground(borderColor); // set text color
        button.setFont(buttonFont); // set font
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 3), // outer border
                BorderFactory.createEmptyBorder(5, 20, 5, 20) // inner padding
        ));
        return button;
    }

    // **** REUSABLE CODE FOR GAME OVER POP UP ABOVE ****

    // ****** THE BUTTONS FOR THE INDIVIDUAL GAMES BELOW ******

    /**
     * methods to display the game over popup for human game
     * this method is implemented in the HumanGame class when the human game is over
     * 
     * @param currentScore the players score achieved in the current game
     * @param scoreToBeat  the high score the player wants to beat
     */
    public static void humanGameOverPopUp(int currentScore, int scoreToBeat) {
        // HTML is used for formatted text with line breaks
        String scoreText = "<html>Your Score: " + currentScore + "<br/>Score to Beat: " + scoreToBeat + "</html>";
        // The action for "PLAY AGAIN" button starts a new HumanGame in a separate
        // thread.
        createGameOverPopUp("Game Over", scoreText, () -> new Thread(() -> {
            HumanGame humanGame = new HumanGame(); // Creating a new instance of HumanGame.
            humanGame.initGame(); // initializing the new game.
        }).start());
    }

    /**
     * method to display the game over popup for bot game
     * this method is implemented in the Bot class when the bot game is over
     * 
     * @param currentScore the bots score achieved in the current game
     */
    public static void botGameOverPopUp(int currentScore) {
        String scoreText = "Bot Score: " + currentScore;
        // The action for "PLAY AGAIN" button starts a new Bot in a separate
        // thread.
        createGameOverPopUp("Game Over", scoreText, () -> new Thread(() -> {
            Bot botGame = new Bot(); // Creating a new instance of Bot.
            botGame.initGame(); // initializing the new game.
        }).start());
    }

    /**
     * method to display the game over popup for sequence game
     * this method is implemented in the BestSequence class when the best sequence
     * game is over
     * 
     * @param currentScore the sequence score achieved in the current game
     */
    public static void sequenceGameOverPopUp(int currentScore) {
        String scoreText = "Best Sequence Score: " + currentScore;
        // The action for "PLAY AGAIN" button starts a new BestSequence in a separate
        // thread.
        createGameOverPopUp("Game Over", scoreText, () -> new Thread(() -> {
            BestSequence bestSequenceGame = new BestSequence(); // Creating a new instance of BestSequence.
            bestSequenceGame.initGame(); // initializing the new game.
        }).start());
    }

    // ****** THE BUTTONS FOR THE INDIVIDUAL GAMES ABOVE ******

}
