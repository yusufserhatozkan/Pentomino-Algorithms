package Phase2;

/**
 * @author Group 8 
 */

/*
 * THIS IS THE BOT GAME VERSION OF TETRIS
 * ** WITHOUT MOVEMENT VISIBLE
 */

import java.util.ArrayList;
import java.util.Arrays;

public class Bot extends TetrisGameArea {
    // stores the new BotTetrisBlock with its different positioning (0,1,2,3,4)
    protected static ArrayList<BotTetrisBlock> currentTetrisBlockXLocation = new ArrayList<>();

    // stores the highest score (aka the x and rotation of the where the alogrithm
    // thinks its best to place the piece)
    protected static BestMove bestMove = new BestMove(Integer.MIN_VALUE, -1, -1);

    // constructor of the Bot
    public Bot() {
        UI botGameUI = new UI(horizontalGridSize, verticalGridSize, 50, false);
        TetrisGameArea.setUI(botGameUI);

        // Initialize the field directly in TetrisGameArea
        TetrisGameArea.field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                TetrisGameArea.field[i][j] = -1;
            }
        }
    }

    // ************************* STUFF THAT CHANGE BELOW ***************************

    /**
     * Copies the original tetris field. We use this right before the next piece is
     * placed onto the field.
     * 
     * @param original pass through the field we see in the UI
     * @return a copy of the current field
     */
    public static int[][] copyField(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    /**
     * Simulates the drop of all 4 different rotations of the block for each x
     * position, 0, 1, 2, 3, 4.
     * 
     * @param botBlock
     * @param simulatedField
     */
    protected static void simulatedDrop(BotTetrisBlock botBlock, int[][] simulatedField) {
        for (int i = 0; i < 4; i++) {
            TetrisBlock blockTetris = botBlock.getTetrisBlocWithRotations(i); // returns the TetrisBlock with the
                                                                              // correct rotation

            if (i == 0) {
                blockTetris = botBlock.getTetrisBlocWithRotations(0);
            }

            if (blockTetris.getWidth() + botBlock.getXPost() > horizontalGridSize) {
                System.out.println("Cannot place block at x =" + botBlock.getXPost() + " with rotation " + i);
                continue; // Skip this rotation because the block would go out of bounds
            }

            // reset the block to the top of the field for each rotation
            blockTetris.setX(botBlock.getXPost()); // the x positioning of where it drops
            blockTetris.setY(0); // the y positioning of where it drops

            if (canMoveDownSimulation(blockTetris, simulatedField)) {
                dropBlock(blockTetris); // drop it to where it can drop
                addToFieldSimulation(blockTetris, simulatedField); // adds it to the simulated field

                // calls the evaluation method for the placement of this rotation at this
                // positioning
                double score = evaluatePlacement(simulatedField);

                System.out.println("Score for this placement: " + blockTetris.getShapeLetter() + " Rotation: " + i
                        + ". X: " + botBlock.getXPost() + ". Score: " + score);

                // if the new generated simulation score is higher, replace the bestMove with
                // the new rotation and x positioning.
                if (score > bestMove.getScore()) {
                    bestMove = new BestMove(score, botBlock.getXPost(), i);
                }
            } else {
                // fails to place it due to array out of bound.
                continue; // it should go do the loop again and not run the removeFromFieldSimulation
                          // because theres nothing to be removed because theres nothing in the array
            }
            // removes the placement of this drop so the next one can drop
            removeFromFieldSimulation(blockTetris, simulatedField);
        }
    }

    // ************************* STUFF THAT CHANGE ABOVE ***************************

    // ************************* SCORES CALCULATION BELOW *************************

    /**
     * 
     * Goal: Maximize the number of rows cleared with each move.
     * Scoring: Assign points for each row that would be cleared by a move.
     * 
     * @param field
     * @return
     */
    public static double rowClearance(int[][] field) {
        int fullRows = 0;

        for (int col = 0; col < verticalGridSize; col++) {
            boolean isFull = true;
            for (int row = 0; row < horizontalGridSize; row++) {
                if (field[row][col] == -1) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                fullRows++;
            }
        }

        return (double) fullRows * 10.3; // weight point is 2
    }

    /**
     * 
     * Goal: Keep the overall stack of blocks as low as possible
     * Scoring: Assign a lower score to moves that result in a higher stack of
     * blocks
     * 
     * @param field
     * @return
     */
    public static double heightMinimization(int[][] field) {
        int maxHeight = 0;

        // iterate through each column
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] != -1) {
                    // The height of the stack in this column is the total height minus the current
                    // y position
                    int stackHeight = field[x].length - y;
                    maxHeight = Math.max(maxHeight, stackHeight);
                    break; // Once the highest block in a column is found, move to the next column
                }
            }
        }
        if (checkHeight(field) == true) {
            return (double) (-maxHeight) * 3.0; // negative score for higher stacks
        } else {
            return (double) (-maxHeight) * 2.4;
        }
    }

    /**
     * a hole is defined as an empty space that has at least one block above it in
     * the same column
     * Goal: Avoid creating holes in the stack
     * Scoring: Deduct points for each hole that would be created by a move
     * 
     * @param field
     * @return
     */
    public static double holeMinimization(int[][] field) {
        int holeCount = 0;

        // iterate through each column
        for (int x = 0; x < field.length; x++) {
            boolean blockFound = false;
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] != -1) {
                    // Once a block is found in a column, set blockFound to true
                    blockFound = true;
                } else if (blockFound && field[x][y] == -1) {
                    // if a block has been found and the current cell is empty, it's a hole
                    holeCount++;
                }
            }
        }

        if (checkHeight(field) == true) {
            return (double) (-holeCount) * 2.3; // negative score for more holes, with a weight of 7
        } else {
            return (double) (-holeCount) * 3.25; // negative score for more holes, with a weight of 7
        }
    }

    // ************************* SCORES CALCULATION ABOVE *************************

    // ************************* EVALUATION BELOW ******************************

    /**
     * checks if the first seven rows of the Tetris grid are empty
     * this method is used to change the evaluation/scoring algorithms based on
     * the height of the play area
     * 
     * @param field the game field (used for the copy evaluation one)
     * @return true if the first seven rows are empty, false otherwise
     */
    public static boolean checkHeight(int[][] field) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if (field[j][i] != -1) { // not -1, so not empty space
                    // if any position in these rows is not empty, return false
                    return false;
                }
            }
        }
        // if all positions in the first seven rows are empty, return true
        return true;
    }

    /**
     * this method adds all the scores of the placement
     * 
     * @param field the simulated field will be passed through here
     * @return the score for the current x and rotation of the TetrisBlock in the
     *         field by adding the evaluated methods
     */
    private static double evaluatePlacement(int[][] field) {
        double score = 0; // initial score is 0
        score += rowClearance(field); // adds the rowClearance score
        score += heightMinimization(field); // adds the heightMinimization score
        score += holeMinimization(field); // adds the holeMinimization score
        return score;
    }

    // ************************* EVALUATION ABOVE ******************************

    // ************************* MANIPULATED METHODS BELOW *************************

    /**
     * Same method as in TetrisGameArea, just the added field to check for the
     * simulated/copied field
     * Adds the pentomino to the array Field which is diplayed in the UI.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void addToFieldSimulation(TetrisBlock block, int[][] field) {
        int h = block.getHeight(); // height of the pentomino array
        int w = block.getWidth(); // width of the pentomino array
        int[][] shape = block.getShape(); // array of the pentomino array
        int num = block.getNum(); // number of pentomino

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) { // if pentomino array has a 1
                    int x = (block.getX() + col); // row in field
                    int y = (block.getY() + row); // col in field
                    field[x][y] = num; // assign num to the position in the field
                }
            }
        }
    }

    /**
     * Same method as in TetrisGameArea, just the added field to check for the
     * simulated/copied field
     * Removes the pentomino which is diplayed in the UI from the array Field .
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @param field
     */
    public static void removeFromFieldSimulation(TetrisBlock block, int[][] field) {
        int[][] shape = block.getShape(); // array of the pentomino array
        int startX = block.getX(); // where the block is located (x) in field UI
        int startY = block.getY(); // where the block is located (y) in field UI

        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (shape[row][col] == 1) {
                    int x = startX + col;
                    int y = startY + row;
                    if (x >= 0 && x < horizontalGridSize && y >= 0 && y < verticalGridSize) {
                        // field[x][y - 1] = -1; // Only remove the part of the block that's active
                        for (int i = 0; i < field.length; i++) {
                            for (int j = 0; j < field[0].length; j++) {
                                if (field[i][j] == block.getNum()) {
                                    field[i][j] = -1;
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    /**
     * Same method as in TetrisGameArea, just the added field to check for the
     * simulated/copied field
     * Checks if the pentomino block can move down by 1.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @param field
     * @return if it can move down, return true, otherwise false.
     */
    public static boolean canMoveDownSimulation(TetrisBlock block, int[][] field) {
        int[][] shape = block.getShape();
        int blockHeight = block.getHeight();
        int blockWidth = block.getWidth();
        int startX = block.getX();
        int startY = block.getY();
        int num = block.getNum();

        for (int row = blockHeight - 1; row >= 0; row--) {
            for (int col = 0; col < blockWidth; col++) {
                if (shape[row][col] == 1) {
                    int x = startX + col;
                    int y = startY + row;

                    // Check if x is within the horizontal bounds of the field
                    if (x < 0 || x >= field.length) {
                        return false;
                    }

                    // Check if y + 1 is within the vertical bounds of the field
                    if (y + 1 >= verticalGridSize || y + 1 >= field[x].length) {
                        return false;
                    }

                    // Check for collision with other blocks
                    if (field[x][y + 1] != -1 && field[x][y + 1] != num) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isOverlapping(TetrisBlock block, int[][] field) {
        int h = block.getHeight(); // height of the pentomino array
        int w = block.getWidth(); // width of the pentomino array
        int[][] shape = block.getShape(); // array of the pentomino array

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                int x = (block.getX() + col); // row in field
                int y = (block.getY() + row); // col in field
                if (shape[row][col] == 1) { // checks if theres a 1 in the pentomino array
                    if (field[x][y] != -1) { // checks if field has a -1 or of its own
                                             // number
                        return true; // if it can find an overlapping case
                    }
                }
            }
        }
        return false; // if it cant find an overlapping case
    }

    // ************************* MANIPULATED METHODS ABOVE *************************

    // ************************* STARTGAME BELOW ******************************

    /**
     * runs the main game loop for the Bot version of the Tetris game
     * handles the game state, block positioning, score calculation, and end game
     * 
     * @param score initial score of the game
     * @param state initial state indicating whether the game is over or not
     * 
     */
    private static void startGame(int score, boolean state) {
        finalScore = score; // set the initial score
        boolean gameOver = state; // initialize a boolean to control the game loop

        while (!gameOver) { // runs until the game is over.
            currentBlock = tetrisArray.get(0); // get the first block in the array as the current block
            nextBlock = tetrisArray.get(1); // get the second block in the array as the next block

            currentBlock = spawnPentomino(); // spawn a new pentomino block

            if (isOverlapping(currentBlock, copyField(field))) { // check for block overlap, indicating game over.
                addToField(currentBlock);
                gameOver = true; // set game over condition
                break; // exit the loop
            }

            ui.displayNextBlock(nextBlock); // display the next block on UI.

            // reset best move parameters before calculation.
            bestMove.setScore(Integer.MIN_VALUE); // set the initial score to the lowest possible value
            bestMove.setRotation(-1); // reset rotation value
            bestMove.setXPosition(-1); // reset x position value
            currentBlock.setY(0); // set initial y position of the block.
            currentBlock.setX(0); // set initial x position of the block.

            // calculate the best move for each possible X position
            for (int i = 0; i < 5; i++) {
                TetrisBlock newBlock = new TetrisBlock(currentBlock); // duplicate the current block
                BotTetrisBlock botBlock = new BotTetrisBlock(newBlock, i); // create a bot block with specific X
                                                                           // position
                currentTetrisBlockXLocation.add(botBlock); // add to list for simulation
            }

            // simulate and evaluate each position for the current block
            for (int i = 0; i < currentTetrisBlockXLocation.size(); i++) {
                System.out.println("**** SIMULATION FOR X-COORDINATE: " + i + " ****");
                simulatedDrop(currentTetrisBlockXLocation.get(i), copyField(field));
            }

            System.out.println("Best Move: " + bestMove.toString()); // debugging

            // rotate the block according to the best move
            // however, if its -1, then dont rotate
            for (int i = 0; i < bestMove.getRotation() && bestMove.getRotation() != -1; i++) {
                System.out.println("Current rotation: " + (i + 1));
                currentBlock.rotate();
            }

            // special case handling for the first block (x=0)
            if (currentBlock.getNum() == 0 && TetrisBlock.getTotalBlockNumber() == 1) {
                currentBlock.setX(0); // Set x position for the first x-block
            } else { // otherwise use the bestMove position
                currentBlock.setX(bestMove.getXPosition()); // set x position based on best move.
            }

            // if its -1. it cant go to position -1, so its out of bound
            // set it to 1
            // this occurs when there is no best movement possible when its at the top
            if (currentBlock.getX() == -1) {
                currentBlock.setX(1);
            }

            // move the block down until it can't move further.
            while (canMoveDown(currentBlock, field)) {
                System.out.println("Current Block State: " + currentBlock.toString());
                addToField(currentBlock); // add block to the field.
                moveBlockDown(currentBlock); // move the block down.
                removeFromField(currentBlock); // remove the block from its previous position.
            }

            // finalize the block's position and update the score.
            setBlockToValue(currentBlock, currentBlock.getNum());
            getScore(); // calculate the score.
            ui.updateScoreDisplay(finalScore); // update the score display.

            // prepare for the next block.
            advanceBlockQueue();
            nextBlock = tetrisArray.get(1); // update the next block.
            ui.displayNextBlock(nextBlock); // display the next block on UI.

            currentTetrisBlockXLocation.clear(); // clear the list for the next round.
        }

        TetrisBlock.setNumberOfBlockToZero(); // reset block count for a new game.
        GameOverPicture.botGameOverPopUp(finalScore); // display game over popup.
    }

    /**
     * initiates the game by calling the startGame method
     */
    public static void initGame() {
        System.out.println("*********BOT GAME HAS STARTED*********");
        startGame(0, false); // starts the game
        // addToField(currentBlock); // adds the last block to the UI
        ui.setState(field); // repaints it
        System.out.println("*********BOT GAME HAS ENDED*********");
    }

    // ************************* STARTGAME ABOVE ******************************

    public static void main(String[] args) {
        Bot botGame = new Bot();
        botGame.initGame();
    }
}

/**
 * Purpose of this class is the creation of the Object BotTetrisBlock.
 * It is useful because it gives us a greater power of overview for the
 * TetrisBlocks that are being used for the simulated evaluation process.
 * We get to control the individual x position and the rotation of the
 * TetrisBlock object. Easier to do the simulation and find the best evlaluation
 * score.
 */
class BotTetrisBlock {
    private TetrisBlock block;
    private int xPos;

    /**
     * 
     * @param block the TetrisBlock object which is the one on the UI field.
     * @param xPos  X positioning of where the block will be dropped/placed in the
     *              simulation field that gets evaluated
     */
    public BotTetrisBlock(TetrisBlock block, int xPos) {
        this.block = block;
        this.xPos = xPos;

        block.setX(xPos);
    }

    /**
     * 
     * @return the x position of the current block
     */
    public int getXPost() {
        return xPos;
    }

    /**
     * 
     * @param rotations rotates the block this many times (1 through 4)
     * @return the rotated block
     */
    public TetrisBlock getTetrisBlocWithRotations(int rotations) {
        TetrisBlock rotatedBlock = new TetrisBlock(block); // Clone the block or create a new one with the same
                                                           // parameters
        for (int i = 0; i < rotations; i++) {
            rotatedBlock.rotate(); // Rotate the clone, not the original
        }
        return rotatedBlock;
    }

    public String toString() {
        return block.getShapeLetter() + ". x-pos: " + block.getX();
    }

}

/**
 * keeps track of the best movement for the pentomino piece
 */
class BestMove {
    private double score;
    private int xPosition;
    private int rotation;

    /**
     * 
     * Stores the score, x-position, and rotation for the best move
     * 
     * @param score     the highest score of the simulated evaluation
     * @param xPosition the x positioning of the highest score of the simulated
     *                  evaluation
     * @param rotation  the rotation of the highest score of the simulated
     *                  evaluation
     */
    public BestMove(double score, int xPosition, int rotation) {
        this.score = score;
        this.xPosition = xPosition;
        this.rotation = rotation;
    }

    /**
     * 
     * @return the current highest score number
     */
    public double getScore() {
        return score;
    }

    /**
     * 
     * @return the current x positioning number of the highest score
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * 
     * @return the rotation number of the highest score
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * 
     * @param score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 
     * @param xPos
     */
    public void setXPosition(int xPos) {
        this.xPosition = xPos;
    }

    /**
     * 
     * @param rotation
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String toString() {
        return "score: " + getScore() + ". xposition: " + getXPosition() + ". rotation: " + getRotation();
    }

}
