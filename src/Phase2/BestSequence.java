package Phase2;
/**
 * @author Group 8 
 */

/*
 * THIS IS THE BEST SEQUENCE GAME VERSION OF TETRIS
 */

import java.util.ArrayList;

public class BestSequence extends TetrisGameArea {
    private static TetrisBlock currentBlock;
    private static final TetrisBlock blockX0 = new TetrisBlock(0);
    private static final TetrisBlock blockI1 = new TetrisBlock(1);
    private static final TetrisBlock blockZ2 = new TetrisBlock(2);
    private static final TetrisBlock blockT3 = new TetrisBlock(3);
    private static final TetrisBlock blockU4 = new TetrisBlock(4);
    private static final TetrisBlock blockV5 = new TetrisBlock(5);
    private static final TetrisBlock blockW6 = new TetrisBlock(6);
    private static final TetrisBlock blockY7 = new TetrisBlock(7);
    private static final TetrisBlock blockL8 = new TetrisBlock(8);
    private static final TetrisBlock blockP9 = new TetrisBlock(9);
    private static final TetrisBlock blockN10 = new TetrisBlock(10);
    private static final TetrisBlock blockF11 = new TetrisBlock(11);
    private static ArrayList<TetrisBlock> arrayOfAllTetrisBlocks = new ArrayList<>();

    // constructor of the BestSequence class
    public BestSequence() {
        UI bestSequenceUI = new UI(horizontalGridSize, verticalGridSize, 50, false);
        setUI(bestSequenceUI);

        // initialize the field directly in TetrisGameArea
        TetrisGameArea.field = new int[horizontalGridSize][verticalGridSize];
        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                TetrisGameArea.field[i][j] = -1;
            }
        }
    }

    // hardcoded best sequence of TetrisBlock objects to be put into the array in
    // the correct order to be then placed onto the UI field
    static { // static block
        arrayOfAllTetrisBlocks.add(blockV5); // v
        arrayOfAllTetrisBlocks.add(blockW6); // w
        arrayOfAllTetrisBlocks.add(blockY7); // y
        arrayOfAllTetrisBlocks.add(blockP9); // p
        arrayOfAllTetrisBlocks.add(blockZ2); // z
        arrayOfAllTetrisBlocks.add(blockT3); // t
        arrayOfAllTetrisBlocks.add(blockF11); // f
        arrayOfAllTetrisBlocks.add(blockX0); // x
        arrayOfAllTetrisBlocks.add(blockN10); // n
        arrayOfAllTetrisBlocks.add(blockU4); // u
        arrayOfAllTetrisBlocks.add(blockL8); // l
        arrayOfAllTetrisBlocks.add(blockI1); // i
    }

    /**
     * hardcode the movement (rotation, left, right, down) for each block
     * if the TetrisBlock has num x, then do specific movements at specific
     * locations
     * 
     * @param block the current TetrisBlock piece that is currently in the
     *              displaeyed in the UI
     */
    public static void casesOfPentomini(TetrisBlock block) {

        // each case represents the different TetrisBlock block
        // each case has different movements
        switch (block.getNum()) { // gets the number of the current block
            case 0:
                System.out.println("X");
                if (block.getY() == 2) {
                    block.moveRight();
                }
                break;
            case 1:
                System.out.println("I");
                if (block.getY() == 3) {
                    block.moveLeft();
                }
                if (block.getY() == 5) {
                    block.rotate();
                }
                break;
            case 2:
                System.out.println("Z");
                if (block.getY() == 2) {
                    block.rotate();
                }
                if (block.getY() == 4) {
                    block.moveLeft();
                }
                break;
            case 3:
                System.out.println("T");
                if (block.getY() == 2) {
                    block.rotate();
                }
                if (block.getY() == 3) {
                    block.moveRight();
                }
                break;
            case 4:
                System.out.println("U");
                if (block.getY() == 3) {
                    block.rotate();
                }
                if (block.getY() == 7) {
                    block.moveRight();
                }
                break;
            case 5:
                System.out.println("V");
                if (block.getY() == 4) {
                    block.moveRight();

                }
                if (block.getY() == 6) {
                    block.rotate();
                }
                if (block.getY() == 8) {
                    block.rotate();
                }
                break;
            case 6:
                System.out.println("W");
                if (block.getY() == 2) {
                    block.moveLeft();
                }

                break;
            case 7:
                System.out.println("Y");
                if (block.getY() == 4) {
                    block.moveRight();
                }
                if (block.getY() == 6) {
                    block.moveRight();
                }
                break;
            case 8:
                System.out.println("L");
                if (block.getY() == 5) {
                    block.rotate();
                }
                break;
            case 9:
                System.out.println("P");
                if (block.getY() == 4) {
                    block.moveLeft();
                }
                break;
            case 10:
                System.out.println("N");
                if (block.getY() == 4) {
                    block.rotate();
                }
                if (block.getY() == 6) {
                    block.moveLeft();
                }
                break;
            case 11:
                System.out.println("F");
                if (block.getY() == 2) {
                    block.rotate();
                }
                if (block.getY() == 3) {
                    block.rotate();
                }
                if (block.getY() == 4) {
                    block.rotate();
                    block.moveLeft();
                }
                break;
        }
    }

    private static void startGame() {
        boolean gameOver = false; // Initialize a flag to track whether the game is over.

        // This loop runs until the game is over.
        while (!gameOver) {
            // Check if there are no more Tetris blocks to play with.
            if (arrayOfAllTetrisBlocks.isEmpty()) {
                gameOver = true; // if no blocks are left, set the game over to true
                System.out.println("No more blocks. Game over.");
                break; // exit the loop
            }

            currentBlock = arrayOfAllTetrisBlocks.get(0); // Retrieve the first block from the array
            arrayOfAllTetrisBlocks.remove(0); // Remove the used block from the array

            // checks if the current block overlaps with existing blocks, which indicates
            // that the game is over
            if (isOverlapping(currentBlock)) {
                gameOver = true; // set gameOver to true to end the loop
                break; // exit the whole loop
            }

            // display the next block if there are still blocks left
            if (!arrayOfAllTetrisBlocks.isEmpty()) {
                ui.displayNextBlock(arrayOfAllTetrisBlocks.get(0));
            }

            // move the block down while it can move down
            while (canMoveDown(currentBlock, field)) {
                addToField(currentBlock); // add the current block to the field
                casesOfPentomini(currentBlock); // specific cases of how they fall
                moveBlockDown(currentBlock); // move the current block down by 1
                removeFromField(currentBlock); // remove the current block from the field
            }

            setBlockToValue(currentBlock, currentBlock.getNum()); // set the block in the field with its new value

            getScore(); // calculate and update the score
            ui.updateScoreDisplay(finalScore); // update the UI with the new score

            advanceBlockQueue(); // advance the block queue for the next turn
            if (!arrayOfAllTetrisBlocks.isEmpty()) {
                nextBlock = blockX0; // prepare the next block for display
                ui.displayNextBlock(nextBlock); // display the next block in the UI
            }
            ui.setState(field);
        }
        TetrisBlock.setNumberOfBlockToZero(); // reset the total number of Tetris blocks when game is over
        GameOverPicture.sequenceGameOverPopUp(finalScore); // display the game over popup with the final score
        ui.setState(field);
    }

    /**
     * initiates the game by calling the startGame method
     */
    public static void initGame() {
        System.out.println("*********BEST SEQUENCE GAME HAS STARTED*********");
        startGame(); // starts the game
        System.out.println("*********BEST SEQUENCE GAME HAS ENDED*********");
    }

    public static void main(String[] args) {
        BestSequence bestSequenceGame = new BestSequence();
        bestSequenceGame.initGame();
    }

}
