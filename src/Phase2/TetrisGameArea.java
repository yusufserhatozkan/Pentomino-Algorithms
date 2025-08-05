package Phase2;

/**
 * @author Group 8 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * THIS IS THE CLASS THAT GETS EXTENDED BY THE HUMANGAME, BOT, BEST SEQUENCE
 * HAS ALL THE PROPERTIES AND METHODS FOR THE TETRIS GAME
 */

public class TetrisGameArea {
    public static int finalScore = 0;

    public static final int horizontalGridSize = 5; // assigns the row
    public static final int verticalGridSize = 15;// assigns the column

    public static List<TetrisBlock> tetrisArray = new ArrayList<>();
    public static TetrisBlock currentBlock; // current active block (shown on the game UI)
    public static TetrisBlock nextBlock; // next block (shown on the small UI panel)

    public static UI ui; // ui with the field
    public static int[][] field; // the field where magic happens

    /**
     * initializes the UI for the different playing areas
     * 
     * @param userInterface the UI interface of the playing area
     */
    public static void setUI(UI userInterface) {
        ui = userInterface;
    }

    // static block initializing the variables above
    static {
        tetrisArray.add(new TetrisBlock());
        tetrisArray.add(new TetrisBlock());
    }

    /**
     * the method makes sure that the arraylist is populated with new tetris blocks
     * and also removes the tetris block once it has been placed onto the UI field
     */
    public static void advanceBlockQueue() {
        // remove the current block (which is now placed on the UI field)
        tetrisArray.remove(0);
        // add a new block to the end of the queue.
        tetrisArray.add(new TetrisBlock());
    }

    /**
     * Prints the current field on the terminal. It first flips the field vertically
     * and then prints it out to the terminal
     */
    public static void printArray(int[][] field) {
        int[][] flipped = new int[field[0].length][field.length]; // converted field to flipped
        // converts to vertical field
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                flipped[j][i] = field[i][j]; // flip x and y

            }
        }
        // prints the vertical field to terminal
        for (int i = 0; i < flipped.length; i++) {
            for (int j = 0; j < flipped[i].length; j++) {
                System.out.print(flipped[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * This method moves the pentomino block down by one.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void moveBlockDown(TetrisBlock block) {
        block.moveDown(); // move down block
        repaint();
    }

    /**
     * Same as the MoveBlockDown method without delay in the repaint of UI.
     * This is made for the key actions for down. so it moves/refresh down faster.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void moveBlockDownFASTER(TetrisBlock block) {
        block.moveDown(); // move down block
    }

    /**
     * This method moves the pentomino block left by one.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void moveBlockLeft(TetrisBlock block) {
        if (canMoveLeft(block)) { // checks if block can move left
            block.moveLeft(); // move left block
        }
    }

    /**
     * This method moves the pentomino block right by one.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void moveBlockRight(TetrisBlock block) {
        if (canMoveRight(block)) { // checks if block can move right
            block.moveRight();// move right block
        }
    }

    /**
     * This method moves the pentomino block to the bottom of the field.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void dropBlock(TetrisBlock block) {
        while (canMoveDown(block, field)) { // checks if block can move down
            block.moveDown(); // move down block
        }
    }

    /**
     * This method rotates the Pentomino block
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void rotateBlock(TetrisBlock block) {
        if (canRotate(block)) { // checks if block can rotate
            block.rotate(); // rotate block
            ui.setState(field);
        } else {
            System.out.println("Rotation not possible!");
        }
    }

    /**
     * function to setState and update the UI.
     * timer for the pentomino falling.
     * manipulate the speed for debugging
     */
    public static void repaint() {
        ui.setState(field);
        ui.updateUI();
        // timer set to 400 ms
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Same as repaint method, just with very little delay.
     * Specifically used for key actions of left, right, rotate
     */
    public static void repaintNoDelay() {
        ui.setState(field);
        ui.updateUI();
        // timer set to 70 ms
        try {
            Thread.sleep(70);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Same as repaint method, just with very little delay.
     * Specifically used for key actions of down
     */
    public static void repaintNoDelayForDroppingKey() {
        ui.setState(field);
        ui.updateUI();
        // timer set to 100 ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method to spawn the pentomino by getting the block in index 0 of the List
     * tetrisArray, which is the current block being placed on game UI
     * 
     * @return a TetrisBlock object operator block which is the current pentomino
     *         that is being played
     */
    public static TetrisBlock spawnPentomino() {
        TetrisBlock block = tetrisArray.get(0);
        System.out.println(
                "Next Pentomino: " + block.getShapeLetter() + ". h: " + block.getHeight() + ". w: " + block.getWidth());
        return block;
    }

    /**
     * Adds the pentomino to the array Field which is diplayed in the UI.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void addToField(TetrisBlock block) {
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
     * Removes the pentomino which is diplayed in the UI from the array Field .
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     */
    public static void removeFromField(TetrisBlock block) {
        int[][] shape = block.getShape(); // array of the pentomino array
        int startX = block.getX(); // where the block is located (x) in field UI
        int startY = block.getY(); // where the block is located (y) in field UI

        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (shape[row][col] == 1) {
                    int x = startX + col;
                    int y = startY + row;
                    if (x >= 0 && x < horizontalGridSize && y >= 0 && y < verticalGridSize) {
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
     * Checks if the pentomino block can move down by 1.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @return if it can move down, return true, otherwise false.
     */
    public static boolean canMoveDown(TetrisBlock block, int[][] field) {
        int[][] shape = block.getShape(); // array of the pentomino array
        int blockHeight = block.getHeight(); // height of the pentomino array
        int blockWidth = block.getWidth(); // width of the pentomino array
        int startX = block.getX(); // where the block is located (x) in field UI
        int startY = block.getY(); // where the block is located (y) in field UI
        int num = block.getNum(); // get the num of the block

        // Iterate through all cells of the pentomino shape
        for (int row = blockHeight - 1; row >= 0; row--) {
            for (int col = 0; col < blockWidth; col++) {
                if (shape[row][col] == 1) { // If this part of the block is filled
                    int x = startX + col; // row in field
                    int y = startY + row; // col in field

                    // check if the block is at the bottom of the field
                    if (y + 1 >= verticalGridSize) {
                        return false; // the entire block can NOT move down
                    }

                    // check if the block can move down without colliding with other blocks
                    if (field[x][y + 1] != -1 && field[x][y + 1] != num) { // checks for -1's and num's
                        return false; // the entire block can NOT move down
                    }
                }
            }
        }
        return true; // the entire block can move down
    }

    /**
     * Checks if the pentomino block can move to the left when the keys are invoked
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @return false if it can not move left, otherwise returns true
     */
    public static boolean canMoveLeft(TetrisBlock block) {
        int[][] shape = block.getShape(); // array of the pentomino array
        int startX = block.getX(); // where the block is located (x) in field UI
        int startY = block.getY(); // where the block is located (y) in field UI

        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (shape[row][col] == 1) {
                    int x = startX + col;
                    int y = startY + row;

                    if (x - 1 < 0 || field[x - 1][y] != -1) {
                        return false; // the entire block can NOT move left
                    }
                }
            }
        }
        return true; // the entire block can move left
    }

    /**
     * Checks if the pentomino block can move to the right when the keys are invoked
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @return false if it can not move right, otherwise returns true
     */
    public static boolean canMoveRight(TetrisBlock block) {
        int[][] shape = block.getShape(); // array of the pentomino array
        int startX = block.getX(); // where the block is located (x) in field UI
        int startY = block.getY(); // where the block is located (y) in field UI

        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = block.getWidth() - 1; col >= 0; col--) {
                if (shape[row][col] == 1) {
                    int x = startX + col;
                    int y = startY + row;

                    if (x + 1 >= horizontalGridSize || field[x + 1][y] != -1) {
                        return false; // the entire block can NOT move right
                    }
                }
            }
        }
        return true; // the entire block can move right
    }

    /**
     * Checks if the pentomino block can rotate when the keys are invoked. if it
     * cant. it will just rotate it back to the original rotation.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @return false if it can not rotate, otherwise returns true
     */
    public static boolean canRotate(TetrisBlock block) {
        block.rotate(); // simulates the rotation
        int[][] rotatedShape = block.getShape(); // array of the rotated pentomino array
        int startX = block.getX(); // where the block is located (x) in field UI
        int startY = block.getY(); // where the block is located (y) in field UI

        // check if any part of the rotated shape is out of bounds or overlaps with
        // existing blocks
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (rotatedShape[row][col] == 1) {
                    int x = startX + col;
                    int y = startY + row;
                    // check bounds and collision
                    if (x < 0 || x >= horizontalGridSize || y < 0 || y >= verticalGridSize || field[x][y] != -1) {
                        block.rotateBack(); // rotate back to the original orientation because rotation is not possible
                        return false; // the entire block can NOT rotate
                    }
                }
            }
        }
        block.rotateBack(); // rotate back to the original orientation
        return true; // the entire block can rotate
    }

    /**
     * Checks for overlapping pentominos at the very top when a new pentomino is
     * spawned.
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @return false if theres a case it is overlapping, otherwise return true
     */
    public static boolean isOverlapping(TetrisBlock block) {
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

    /**
     * A setter method that takes the current num of the block and converts it to a
     * new number
     * This is done for overlapping reasons
     * 
     * @param block which is the Object in TetrisBlock that is being passed through
     * @param num   is the current blocks number
     */
    public static void setBlockToValue(TetrisBlock block, int num) {
        int converter = 0;

        // converts the num it was assinged to to a new num displayed on the field.
        converter = PentominoBuilder.getConvertedNum(num);

        int[][] shape = block.getShape(); // array of the pentomino array
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (shape[row][col] == 1) { // if pentomino array has a 1
                    int x = (block.getX() + col); // row in field
                    int y = (block.getY() + row); // col in field
                    if (x >= 0 && x < horizontalGridSize && y >= 0 && y < verticalGridSize) {
                        field[x][y] = converter; // set new value to the position in the field
                    }
                }
            }
        }
    }

    /**
     * this method calculates and records the score based on completed lines in
     * the game field.
     */
    public static void getScore() {
        int filled, score = 0, column = 14, modifiedScore = 0;
        while (column >= 0) {
            filled = 5; // number of places per column

            // count how many places in the column are filled
            for (int i = 0; i < 5; i++) {
                if (field[i][column] != -1)
                    filled--;
            }

            // check if the column is completely filled
            if (filled == 0) {
                ui.setState(field);
                // delay the animation
                try {
                    Thread.sleep(500); // delay of 500 milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // remove the filled line and update the score
                removeFilledLine(field, column);
                score++;
            }

            // update the modified score and goes to the next column
            if (modifiedScore < score) {
                modifiedScore = score;
            } else {
                column--;
            }
        }
        // update the final score by multiplying each line score by 100
        finalScore += score * 100;
    }

    /**
     * this method removes a filled line from the UI field and adjusts it
     * 
     * @param field  the UI field after the piece dropped
     * @param column the column number that is filled
     */
    public static void removeFilledLine(int[][] field, int column) {
        if (column == 0) {
            // if the column to be removed is the first one, simply set its values to -1
            for (int i = 0; i < 5; i++) {
                field[i][column] = -1;
            }
        } else {
            // shift all the columns above the removed column down by one
            for (int j = column; j > 0; j--)
                for (int i = 0; i < 5; i++) {
                    field[i][j] = field[i][j - 1];
                }
        }

        // set the first column to -1 after the shift
        for (int i = 0; i < 5; i++)
            field[i][0] = -1;
        column = column + 1;
    }

}