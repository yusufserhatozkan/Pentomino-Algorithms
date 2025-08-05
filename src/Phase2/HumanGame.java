package Phase2;

/**
 * @author Group 8 
 */

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * THIS IS THE HUMAN GAME VERSION OF TETRIS
 */

public class HumanGame extends TetrisGameArea {

    public HumanGame() {
        UI humanGameUI = new UI(horizontalGridSize, verticalGridSize, 50, true);
        setUI(humanGameUI); // Set the UI in TetrisGameArea

        // Initialize the field directly in TetrisGameArea
        TetrisGameArea.field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                TetrisGameArea.field[i][j] = -1;
            }
        }
    }

    private static void startGame(int score, boolean state) {
        finalScore = score;
        boolean gameOver = state; // initalize a boolean to stop the while loop when game is over

        while (!gameOver) { // runs until game is NOT over

            currentBlock = tetrisArray.get(0); // Get the first block as the current block.
            nextBlock = tetrisArray.get(1); // Get the second block as the next block.

            currentBlock = spawnPentomino(); // creates a new pentomino block
            if (isOverlapping(currentBlock)) { // checks for overlapping
                gameOver = true; // stops the game
                break; // breaks the loop
            }

            ui.displayNextBlock(nextBlock); // Call this method after nextBlock is known to be non-null

            while (canMoveDown(currentBlock, field)) { // checks if it can move down

                System.out.println(currentBlock.toString()); // debugging purposes
                addToField(currentBlock);
                moveBlockDown(currentBlock); // move down the pentomino by 1
                removeFromField(currentBlock); // removes the pentomino
            }

            setBlockToValue(currentBlock, currentBlock.getNum());

            getScore();
            ui.updateScoreDisplay(finalScore);

            advanceBlockQueue(); // removes block from array and adds a new one
            nextBlock = tetrisArray.get(1);
            ui.displayNextBlock(nextBlock);
        }

        String filePath = "scores.csv"; // path to the scores file
        List<Integer> scores = ScoreReader.readScores(filePath); // read existing scores
        scores.add(finalScore); // add the final score of the current game
        ScoreWriter.writeScores(scores, filePath); // write updated scores back to the file

        // checks the best score and uses it to display in the game over
        List<Integer> topScores = ScoreManager.getTopScores(scores, 1);
        int top = topScores.get(0);

        TetrisBlock.setNumberOfBlockToZero();
        GameOverPicture.humanGameOverPopUp(finalScore, top); // the game over pop up.

        return;
    }

    public static void restartGame() {
        TetrisGameArea.field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                TetrisGameArea.field[i][j] = -1;
            }
        }
        ui.setState(field);

        finalScore = 0;
        tetrisArray.clear();
        tetrisArray.add(new TetrisBlock());
        tetrisArray.add(new TetrisBlock());
        ui.setState(field);
        // Start the game
        initGame();
        ui.setState(field);

    }

    public static void initGame() {
        System.out.println("*********HUMAN GAME HAS STARTED*********");

        startGame(0, false); // starts the game
        addToField(currentBlock); // places the 'ending' pentomino to the field
        ui.setState(field); // repaints it

        System.out.println("*********HUMAN GAME HAS ENDED*********");

        System.out.println("Total Pentomino Blocks: " + TetrisBlock.getTotalBlockNumber());

    }

    public static void main(String[] args) {
        HumanGame humanGame = new HumanGame();
        humanGame.initGame();
    }
}
