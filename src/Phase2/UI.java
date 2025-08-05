package Phase2;

/**
 * fgvhj
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * this class takes care of all the graphics to display a certain state.
 */
public class UI extends JPanel {
    private static final int BORDER_WIDTH = 20;
    private JFrame window;
    private int[][] state;
    private int size;
    private JPanel sidePanel;
    private JPanel nextPentominoDisplay;
    private JLabel scoreValue;

    private int goldBorder = 8; // the golden border width

    /**
     * The designated colors for the UI
     */
    private final Color backgroundColor = new Color(46, 64, 83); // dark background color
    private final Color panelColor = new Color(0, 0, 0, 0); // transparent for panels
    private final Color borderColor = new Color(255, 215, 0); // gold/yellow border
    private final Color textColor = new Color(255, 215, 0); // gold/yellow text
    private final Color rightPanel = backgroundColor;

    /**
     * Constructor for the GUI. Sets everything up
     * 
     * @param x                x position of the GUI
     * @param y                y position of the GUI
     * @param _size            size of the GUI
     * @param enableKeyBinding for human game its true, because key binding is used
     *                         only for that game mode
     */
    public UI(int x, int y, int _size, boolean enableKeyBindings) {
        size = _size;
        setPreferredSize(new Dimension(x * size + 1 * BORDER_WIDTH, y * size + 0 * BORDER_WIDTH));

        window = new JFrame("Pentomino Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, BORDER_WIDTH, backgroundColor));
        String tetris = "TETRIS";

        VerticalTextPanel verticalTextPanel = new VerticalTextPanel(tetris, new Color(255, 215, 0), 115);
        window.add(verticalTextPanel, BorderLayout.WEST); // adds the text panel to the left side of the main frame

        // create a new container JPanel
        JPanel container = new JPanel(new BorderLayout());
        // set the background color of the container panel
        container.setBackground(backgroundColor); //
        // set an empty border to the container panel to act as the border
        container.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        configureSidePanel(y, 150, 150, 50, 50);
        container.add(this, BorderLayout.CENTER);
        container.add(sidePanel, BorderLayout.EAST);
        window.add(container);

        window.pack();
        window.setVisible(true);

        initializeState(x, y);
        if (enableKeyBindings) { // if true, then the keyboard keys are activated
            setupKeyBindings(); // in charge of the keyboard keys
        }

    }

    // for character keys
    private void bindKeyWithAction(char keyChar, String actionName, ActionListener actionListener) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyChar), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });
    }

    // For special keys
    private void bindKeyWithAction(int keyCode, String actionName, ActionListener actionListener) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });
    }

    /**
     * This method binds specific key strokes to the game: left arrow, down arrow,
     * right
     * arrow, up arrow, spacebar, a, s, d, w.
     */
    private void setupKeyBindings() {
        bindKeyWithAction(KeyEvent.VK_LEFT, "moveLeftArrow", e -> handleMoveLeft());
        bindKeyWithAction(KeyEvent.VK_RIGHT, "moveRightArrow", e -> handleMoveRight());
        bindKeyWithAction(KeyEvent.VK_DOWN, "moveDownArrow", e -> handleMoveDown());
        bindKeyWithAction(KeyEvent.VK_UP, "rotateArrow", e -> handleRotate());

        bindKeyWithAction(KeyEvent.VK_SPACE, "dropDownSpace", e -> handleDrop());
        bindKeyWithAction(KeyEvent.VK_ENTER, "dropDownSpace", e -> handleDrop());

        bindKeyWithAction('a', "moveLeftChar", e -> handleMoveLeft());
        bindKeyWithAction('d', "moveRightChar", e -> handleMoveRight());
        bindKeyWithAction('s', "moveDownChar", e -> handleMoveDown());
        bindKeyWithAction('w', "rotateChar", e -> handleRotate());
    }

    /**
     * the brain behind what methods should be called when the left key is stroked
     */
    private void handleMoveLeft() {
        TetrisGameArea.removeFromField(TetrisGameArea.currentBlock);
        TetrisGameArea.moveBlockLeft(TetrisGameArea.currentBlock);
        TetrisGameArea.addToField(TetrisGameArea.currentBlock);
        TetrisGameArea.repaintNoDelay();
    }

    /**
     * the brain behind what methods should be called when the right key is stroked
     */
    private void handleMoveRight() {
        TetrisGameArea.removeFromField(TetrisGameArea.currentBlock);
        TetrisGameArea.moveBlockRight(TetrisGameArea.currentBlock);
        TetrisGameArea.addToField(TetrisGameArea.currentBlock);
        TetrisGameArea.repaintNoDelay();
    }

    /**
     * the brain behind what methods should be called when the down key is stroked
     */
    private void handleMoveDown() {
        if (TetrisGameArea.canMoveDown(TetrisGameArea.currentBlock, TetrisGameArea.field)) {
            TetrisGameArea.removeFromField(TetrisGameArea.currentBlock);
            TetrisGameArea.moveBlockDownFASTER(TetrisGameArea.currentBlock);
            TetrisGameArea.addToField(TetrisGameArea.currentBlock);
            TetrisGameArea.repaintNoDelayForDroppingKey();
        }
    }

    /**
     * the brain behind what methods should be called when the up key is stroked
     */
    private void handleRotate() {
        TetrisGameArea.removeFromField(TetrisGameArea.currentBlock);
        TetrisGameArea.rotateBlock(TetrisGameArea.currentBlock);
        TetrisGameArea.addToField(TetrisGameArea.currentBlock);
        TetrisGameArea.repaintNoDelay();
    }

    /**
     * the brain behind what methods should be called when the space key is stroked
     */
    private void handleDrop() {
        if (TetrisGameArea.canMoveDown(TetrisGameArea.currentBlock, TetrisGameArea.field)) {
            TetrisGameArea.removeFromField(TetrisGameArea.currentBlock);
            TetrisGameArea.dropBlock(TetrisGameArea.currentBlock);
            TetrisGameArea.addToField(TetrisGameArea.currentBlock);
        }
    }

    /**
     * configures the right side panel of the window with panels
     * 
     * @param y                     the height factor used to set the preferred size
     *                              of the side panel
     * @param nextPentominoHeight   the height for the Next Pentomino panel
     * @param scorePanelHeight      the height for the score display panel
     * @param highScoresPanelHeight the height for the high scores display panel
     * @param buttonPanelHeight     the height for the button panel
     */
    private void configureSidePanel(int y, int nextPentominoHeight, int scorePanelHeight,
            int highScoresPanelHeight, int buttonPanelHeight) {
        sidePanel = new JPanel(); // initialize the sidePanel

        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));

        sidePanel.setPreferredSize(new Dimension(250, y * size));
        // set the background color of the panel
        sidePanel.setBackground(panelColor);
        sidePanel.setBorder(BorderFactory.createEmptyBorder());

        // create and add the Next Pentomino panel to the side panel
        sidePanel.add(createNextPentominoPanel(nextPentominoHeight));

        // add area to act as a spacer/block
        sidePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // create and add the Current Score panel to the side panel
        sidePanel.add(createScorePanel(scorePanelHeight));

        // add area to act as a spacer/block
        sidePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // create and add the High Scores panel to the side panel
        sidePanel.add(createHighScoresPanel(highScoresPanelHeight));

        // add the sidePanel to the main window on the right side (East)
        window.add(sidePanel, BorderLayout.EAST);
    }

    /**
     * The next pentomino panel on the right side of the game area UI
     * 
     * @param height
     * @return
     */
    private JPanel createNextPentominoPanel(int height) {
        JPanel nextPentominoPanel = new JPanel();
        nextPentominoPanel.setLayout(new BorderLayout());
        nextPentominoPanel.setPreferredSize(new Dimension(400, height));
        nextPentominoPanel.setBackground(panelColor);

        Border outerBorder = BorderFactory.createMatteBorder(goldBorder, goldBorder, goldBorder, goldBorder,
                borderColor);
        Border innerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        nextPentominoPanel.setBorder(compoundBorder);

        JLabel nextLabel = new JLabel("NEXT PENTOMINO", SwingConstants.CENTER);
        nextLabel.setForeground(textColor);
        nextLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Matching the font size and style of "CURRENT SCORE"

        // Creating a compound border for the label to include both the yellow border
        // and internal padding
        Border labelBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, goldBorder, 0, borderColor),
                BorderFactory.createEmptyBorder(10, 0, 10, 0));
        nextLabel.setBorder(labelBorder);

        nextPentominoPanel.add(nextLabel, BorderLayout.NORTH);

        JPanel nextPentominoContentPanel = new JPanel();
        nextPentominoContentPanel.setBackground(backgroundColor);
        // Add content or components related to the next pentomino here if needed
        nextPentominoPanel.add(nextPentominoContentPanel, BorderLayout.CENTER);

        nextPentominoDisplay = new JPanel(new GridLayout(5, 5)); // Assume a 5x5 grid for the display
        nextPentominoDisplay.setBackground(panelColor);
        nextPentominoPanel.add(nextPentominoDisplay, BorderLayout.CENTER);

        return nextPentominoPanel;
    }

    /**
     * displays the next block in a the next pentomino field
     * 
     * @param block The TetrisBlock object to be displayed
     */
    public void displayNextBlock(TetrisBlock block) {
        nextPentominoDisplay.removeAll(); // clear the panel before adding new components

        int[][] shape = block.getShape(); // get the array of the blocks shape
        int shapeHeight = shape.length; // height of the shape
        int shapeWidth = shape[0].length; // width of the shape

        // calculate the top-left start position to center the shape in a 5x5 grid.
        int startRow = (5 - shapeHeight) / 2;
        int startCol = (5 - shapeWidth) / 2;

        // initialize a 5x5 boolean grid with false
        boolean[][] grid = new boolean[5][5];

        // mark cells in the grid as true where there are "1" (not empty) filled
        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeWidth; j++) {
                if (shape[i][j] == 1) { // check if the shape occupies this cell
                    grid[startRow + i][startCol + j] = true; // makes it true
                }
            }
        }

        // create and add JLabels to the panel to visually represent the 5x5 grid
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                JLabel label = new JLabel();
                label.setOpaque(true); // make label background visible
                label.setPreferredSize(new Dimension(size, size)); // set label size
                label.setBorder(null); // remove label borders

                // set the background color of the label (true == color, false == no color)
                if (grid[row][col]) { // if true
                    label.setBackground(GetColorOfID(block.getNum())); // color for the specific block
                } else { // if fasle
                    label.setBackground(rightPanel); // default background color
                }
                nextPentominoDisplay.add(label); // add label to the display panel
            }
        }
        // refresh and repaint the panel to show the updated display
        nextPentominoDisplay.revalidate();
        nextPentominoDisplay.repaint();
    }

    /**
     * the create score panel on the right side of the game area UI
     * 
     * @param height heght of the score panel
     * @return the Jpanel for with all the components in it for the score panel
     */
    private JPanel createScorePanel(int height) {
        // MAIN SCORE PANEL SETUP
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS)); // Use BoxLayout
        scorePanel.setPreferredSize(new Dimension(400, height));
        scorePanel.setBackground(panelColor);
        scorePanel.setBorder(
                BorderFactory.createMatteBorder(goldBorder, goldBorder, goldBorder, goldBorder, borderColor));

        // TITLE LABEL FOR CURRENT SCORE
        JLabel titleLabel = new JLabel("CURRENT SCORE", SwingConstants.CENTER);
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Adjust font size as needed
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, left, bottom, right padding
        scorePanel.add(titleLabel);

        // SEPERATOR PANEL
        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(new Dimension(scorePanel.getWidth(), goldBorder)); // height to goldBorder
        separatorPanel.setBorder(BorderFactory.createMatteBorder(goldBorder, 0, 0, 0, borderColor)); // only top border
        separatorPanel.setOpaque(false); // make the panel transparent
        separatorPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // center the separator panel
        scorePanel.add(separatorPanel);

        // Filler panel for spacing above score value
        scorePanel.add(Box.createVerticalGlue());

        // SCORE VALUE SETUP
        scoreValue = new JLabel("0", SwingConstants.CENTER); // Start with 0 score
        scoreValue.setForeground(textColor);
        scoreValue.setOpaque(true);

        scoreValue.setBackground(backgroundColor);
        scoreValue.setFont(new Font("Arial", Font.BOLD, 30));
        scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePanel.add(scoreValue);

        // Filler panel for spacing below score value
        scorePanel.add(Box.createVerticalGlue());

        return scorePanel;
    }

    /**
     * updates the text of the score display label to show the new score
     * 
     * @param newScore the new score value to display
     */
    public void updateScoreDisplay(int newScore) {
        scoreValue.setText(String.valueOf(newScore)); // set scoreValue Jlabel to new score
    }

    /**
     * the highscore panel on the right side of the game area UI
     * 
     * @param height height of the panel
     * @return Jpanel of the highscore panel of the UI
     */
    private JPanel createHighScoresPanel(int height) {
        // Main high scores panel setup
        JPanel highScoresPanel = new JPanel();
        highScoresPanel.setLayout(new BorderLayout());
        highScoresPanel.setPreferredSize(new Dimension(400, height)); // prefrenced size
        highScoresPanel.setBackground(panelColor);
        highScoresPanel.setBorder(
                BorderFactory.createMatteBorder(goldBorder, goldBorder, goldBorder, goldBorder, borderColor));

        // TITLE TABLE FOR TOP 5
        JLabel titleLabel = new JLabel("TOP 5", SwingConstants.CENTER);
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        highScoresPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(new Dimension(highScoresPanel.getWidth(), goldBorder));
        separatorPanel.setBorder(BorderFactory.createMatteBorder(goldBorder, 0, 0, 0, borderColor));
        separatorPanel.setOpaque(false);
        highScoresPanel.add(separatorPanel, BorderLayout.CENTER);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        scoresPanel.setBackground(backgroundColor);
        scoresPanel.add(Box.createVerticalGlue());

        // read scores and get top 5
        String filePath = "scores.csv"; // file path for scores
        List<Integer> scores = ScoreReader.readScores(filePath); // stores all scores
        List<Integer> topScores = ScoreManager.getTopScores(scores, 5); // stores top scores

        // create a string to display scores
        String scoresStr = "<html>"; // start with CSS/HTML code
        // add this to the JPanel
        for (int i = 0; i < topScores.size(); i++) { // i is the number (1,2,3,4,5) on the scoreboard
            scoresStr += (i + 1) + ". " + topScores.get(i) + "<br>"; // <br> is next line in CSS
        }
        scoresStr += "</html>"; // end with HTML/CSS code

        JLabel top5Values = new JLabel(scoresStr, SwingConstants.CENTER);
        top5Values.setForeground(textColor);
        top5Values.setFont(new Font("Arial", Font.BOLD, 20));
        top5Values.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresPanel.add(top5Values);

        scoresPanel.add(Box.createVerticalGlue());
        highScoresPanel.add(scoresPanel, BorderLayout.SOUTH);

        return highScoresPanel;
    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the
     * state stored by the UI class.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor);

        Graphics2D localGraphics2D = (Graphics2D) g;

        Stroke thinStroke = new BasicStroke(1.0f);

        localGraphics2D.setStroke(thinStroke);

        localGraphics2D.setColor(borderColor);
        localGraphics2D.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        localGraphics2D.setColor(Color.GRAY);
        localGraphics2D.fill(new Rectangle(1, 1, getWidth() - 2, getHeight() - 2));

        for (int i = 0; i <= state.length; i++) {
            localGraphics2D.drawLine(i * size, 0, i * size, state[0].length * size);
        }

        for (int i = 0; i <= state[0].length; i++) {
            localGraphics2D.drawLine(0, i * size, state.length * size, i * size);
        }

        // draw blocks
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(i * size + 1, j * size + 1, size - 1, size - 1));
            }
        }
    }

    /**
     * Decodes the ID of a pentomino into a color
     * Pentominos have 2 different ID nums for overlapping reasons
     * Thats why there are 2 ID's for each color
     * 
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color
     */
    private static Color GetColorOfID(int i) {
        if (i == 0 || i == 12 || i == 24) {
            return Color.BLUE;
        } else if (i == 1 || i == 13) {
            return Color.ORANGE;
        } else if (i == 2 || i == 14) {
            return Color.CYAN;
        } else if (i == 3 || i == 15) {
            return Color.GREEN;
        } else if (i == 4 || i == 16) {
            return Color.MAGENTA;
        } else if (i == 5 || i == 17) {
            return Color.PINK;
        } else if (i == 6 || i == 18) {
            return Color.RED;
        } else if (i == 7 || i == 19) {
            return Color.YELLOW;
        } else if (i == 8 || i == 20) {
            return new Color(0, 0, 0); // black
        } else if (i == 9 || i == 21) {
            return new Color(0, 0, 100); // dark blue
        } else if (i == 10 || i == 22) {
            return new Color(100, 0, 0); // dark red
        } else if (i == 11 || i == 23) {
            return new Color(0, 100, 0); // dark green
        } else if (i == 99) {
            return new Color(153, 50, 204);
        } else {
            return Color.LIGHT_GRAY;
        }
    }

    /**
     * a getter method to get the Color of specific num
     * 
     * @param num the num of the specific tetris block
     * @return the specific Color of for this block
     */
    public static Color getGetColorOfID(int num) {
        return GetColorOfID(num);
    }

    private void initializeState(int x, int y) {
        state = new int[x][y];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    /**
     * This function should be called to update the displayed state (makes a copy)
     * 
     * @param _state information about the new state of the GUI
     */
    public void setState(int[][] _state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = _state[i][j];
            }
        }
        repaint();
    }

}

/**
 * class representing JPanel that displays the TETRIS text vertically
 * 
 * refrence: https://docs.oracle.com/javase/8/docs/api/java/awt/FontMetrics.html
 * refrence:
 * https://eng.libretexts.org/Bookshelves/Computer_Science/Programming_Languages/Java_Java_Java_-_Object-Oriented_Programming_(Morelli_and_Walde)/07%3A_Strings_and_String_Processing/7.07%3A_Handling_Text_in_a_Graphics_Context_(Optional)
 * refrence:
 * https://www.daniweb.com/programming/software-development/threads/141804/trying-to-draw-text-at-an-angle
 * 
 */
class VerticalTextPanel extends JPanel {
    // class fields to store text properties
    private String text; // TETRIS
    private Color textColor; // color
    private Font textFont; // font
    private int textWidth; // width

    /**
     * constructor for VerticalTextPanel with text, color, and
     * font size for the TETRIS text on the UI
     *
     * @param text      the text to be displayed (TETRIS)
     * @param textColor the color of the text
     * @param fontSize  the size of the font
     */
    public VerticalTextPanel(String text, Color textColor, int fontSize) {
        this.text = text;
        this.textColor = textColor;
        this.textFont = new Font("Arial", Font.BOLD, fontSize);
        this.setBackground(new Color(46, 64, 83)); // dets the background color of the panel

        // FontMetrics is used to measure the properties of the specified font
        FontMetrics metrics = this.getFontMetrics(textFont);
        // calculate the width needed for the vertical text.
        textWidth = metrics.getHeight() + 200;

        // set the preferred size of the panel, adding some padding around the text
        this.setPreferredSize(new Dimension(textWidth - 70, this.getPreferredSize().height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // set the color and font for the text
        g2d.setColor(textColor);
        g2d.setFont(textFont);

        FontMetrics fontmetr = g2d.getFontMetrics();
        // the vertical starting position is set to the font's ascent
        int startY = fontmetr.getAscent(); // ascent is the distance from baseline to alphametric characer

        // find the maximum width of a character in the text for center alighemnt later
        int maxCharWidth = 0;
        for (char c : text.toCharArray()) {
            if (fontmetr.charWidth(c) > maxCharWidth) {
                maxCharWidth = fontmetr.charWidth(c);
            }
        }

        // calculate the center position for the widest character
        int centerX = (getWidth() - maxCharWidth) / 2;

        // draw each character of the text vertically.
        for (char c : text.toCharArray()) {
            int charWidth = fontmetr.charWidth(c);
            // adjust the horizontal position for each character
            int x = centerX + (maxCharWidth - charWidth) / 2;
            g2d.drawString(String.valueOf(c), x, startY);
            // increment the vertical position for the next character
            startY += fontmetr.getHeight();
        }
    }
}
