package Phase2;

/**
 * @author Group 8
 */

/*
 * THIS IS THE BOT GAME VERSION OF TETRIS
 * ** WITH MOVEMENT VISIBLE
 */

public class BotMovement extends Bot {

    // constructor inheriting from the Bot class
    public BotMovement() {
        super(); // of the Bot Object in Bot class
    }

    /**
     * movement from where it spawns (x=1) to the designated x coordinate, also the
     * rotation. These are the values from the bestMove, as with these two
     * varaibeles the best score was found (aka the best positioning).
     * 
     * @param bestMove gives us the x positioning and rotation we must use to move
     *                 the block
     */
    public static void movementFrom(BestMove bestMove) {

        int rotationsNeeded = bestMove.getRotation(); // number of rotation needed for the block
        int targetXPosition = bestMove.getXPosition(); // target x position for the block

        // if its -1. it cant go to position -1 or rotation -1
        // set it to 0, 1
        // this occurs when there is no best movement possible when its at the top
        if (rotationsNeeded == -1 || targetXPosition == -1) {
            rotationsNeeded = 0;
            targetXPosition = 1;
        }

        int currentRotation = 0; // tracks the current rotations

        // attempt rotations when the block is at a y=1
        if (currentBlock.getY() == 1) {

            while (currentRotation < rotationsNeeded) {
                if (canRotate(currentBlock)) {
                    currentRotation++;
                    currentBlock.rotate(); // rotates the block if possible
                    ui.setState(field);
                } else {
                    // tries to move left if rotation is not possible
                    boolean movedLeft = false;
                    while (canMoveLeft(currentBlock) && !canRotate(currentBlock)) {
                        currentBlock.moveLeft();
                        ui.setState(field);
                        movedLeft = true;
                    }
                    // if a rotation is possible after moving left, rotate the block
                    if (canRotate(currentBlock)) {
                        currentRotation++;
                        currentBlock.rotate();
                    } else if (movedLeft) {
                        // moves back to the original position
                        while (currentBlock.getX() < bestMove.getXPosition() && canMoveRight(currentBlock)) {
                            currentBlock.moveRight();
                            ui.setState(field);
                        }
                    }

                    // tries to move right if rotation is not possible
                    boolean movedRight = false;
                    while (canMoveRight(currentBlock) && !canRotate(currentBlock)) {
                        currentBlock.moveRight();
                        ui.setState(field);
                        movedRight = true;
                    }
                    // if a rotation is possible after moving right, rotate the block
                    if (canRotate(currentBlock)) {
                        currentRotation++;
                        currentBlock.rotate();
                        ui.setState(field);
                    } else if (movedRight) {
                        // move back to the original position
                        while (currentBlock.getX() > bestMove.getXPosition() && canMoveLeft(currentBlock)) {
                            currentBlock.moveLeft();
                            ui.setState(field);
                        }
                    }
                }
            }
        }

        // move the block horizontally to the target x position
        if (currentBlock.getY() == 2) {
            // moves left
            while (currentBlock.getX() > targetXPosition && canMoveLeft(currentBlock)) {
                currentBlock.moveLeft();
                ui.setState(field);
            }
            // moves right
            while (currentBlock.getX() < targetXPosition && canMoveRight(currentBlock)) {
                currentBlock.moveRight();
                ui.setState(field);
            }
        }
    }

    /**
     * runs the main game loop for the Bot Movement version of the Tetris game
     * handles the game state, block movements, score calculation, and end game
     *
     * @param score initial score for the game
     * @param state initial state indicating whether the game is over or not
     */
    private static void startGame(int score, boolean state) {
        finalScore = score; // set the final score of the game
        boolean gameOver = state; // initialize a boolean to control the game loop

        // run the game loop until the game is over
        while (!gameOver) {
            currentBlock = tetrisArray.get(0); // get the first block as the current block
            nextBlock = tetrisArray.get(1); // get the second block as the next block

            // checks if the current block overlaps with existing blocks, which indicates
            // that the game is over
            if (isOverlapping(currentBlock)) {
                gameOver = true; // set gameOver to true to end the loop
                break; // exit the whole loop
            }

            // display the next block in the UI
            ui.displayNextBlock(nextBlock);

            // reset bestMove values before calculating the best move
            bestMove.setScore(Integer.MIN_VALUE); // set the initial score to the lowest possible value
            bestMove.setRotation(-1); // reset rotation value
            bestMove.setXPosition(-1); // reset x position value

            // iterate through all possible x positions for the current block
            for (int i = 0; i < 5; i++) {
                TetrisBlock newBlock = new TetrisBlock(currentBlock); // create a new block based on the current block
                BotTetrisBlock botBlock = new BotTetrisBlock(newBlock, i); // create a bot block with the specified x
                                                                           // position (x:0,1,2,3,4)
                currentTetrisBlockXLocation.add(botBlock); // add the bot block to the list for simulation
            }

            // simulate and evaluate each position for the current block
            for (int i = 0; i < currentTetrisBlockXLocation.size(); i++) {
                System.out.println("**** SIMULATION FOR X-COORDINATE: " + i + " ****");
                simulatedDrop(currentTetrisBlockXLocation.get(i), copyField(field)); // perform the drop simulation
            }

            System.out.println("!!!***!!!***!!!" + bestMove.toString() + "!!!***!!!***!!!"); // debugging

            // special case handling for the first block (x=0)
            if (currentBlock.getNum() == 0 && TetrisBlock.getTotalBlockNumber() == 1) {
                currentBlock.setX(0); // Set x position for the first x-block
            } else { // otherwise use the bestMove position
                currentBlock.setX(bestMove.getXPosition()); // set x position based on best move.
            }

            // move the block down while it can move down
            while (canMoveDown(currentBlock, field)) {
                System.out.println(currentBlock.toString()); // debugging: print the current block's info
                movementFrom(bestMove); // perform movements based on the best move

                addToField(currentBlock); // add the current block to the field
                moveBlockDown(currentBlock); // move the current block down by 1

                removeFromField(currentBlock); // remove the current block from the field
            }

            setBlockToValue(currentBlock, currentBlock.getNum()); // set the block in the field with its value
            getScore(); // calculate and update the score
            ui.updateScoreDisplay(finalScore); // update the UI with the new score

            advanceBlockQueue(); // advance the block queue for the next turn
            nextBlock = tetrisArray.get(1); // get the new next block
            ui.displayNextBlock(nextBlock); // display the new next block in the UI

            currentTetrisBlockXLocation.clear(); // clear the list of bot blocks for the next turn
        }
        TetrisBlock.setNumberOfBlockToZero(); // reset the total number of Tetris blocks when game is over
        GameOverPicture.botGameOverPopUp(finalScore); // display the game over popup with the final score
    }

    /**
     * initiates the game by calling the startGame method
     */
    public static void initGame() {
        System.out.println("*********BOT MOVEMENT GAME HAS STARTED*********");
        startGame(0, false); // starts the game
        addToField(currentBlock); // adds the last block to the UI
        ui.setState(field); // repaints it
        System.out.println("*********BOT MOVEMENT GAME HAS ENDED*********");
    }

    public static void main(String[] args) {
        BotMovement botGameMovement = new BotMovement();
        botGameMovement.initGame();
    }
}