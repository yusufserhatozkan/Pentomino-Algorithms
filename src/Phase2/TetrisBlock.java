package Phase2;

/**
 * @author Group 8 
 */

import java.util.Arrays;
import java.util.Random;

/*
 * THIS IS THE OBJECT FOR THE INDIVIDUAL TETRISBLOCK THAT SPAWN IN THE UI FIELD
 */

public class TetrisBlock {
    private int[][] pentomino; // the shape/block of the pentomino
    private int x; // x-position while pentromino moves in the UI
    private int y; // y-positon while pentromino moves in the UI
    private int num; // ID number
    private int blockNum; // the number of pentomino on the field
    private int currentRotation; // the current rotation
    private static int numberOfBlocks = 0; // total number of blocks

    /**
     * TetrisBlock constructor that initalizes num, pentomino, x, y, and blockNum;
     */
    public TetrisBlock() {
        // randomizes a number from 1-12 for the pentomoinos
        Random random = new Random();
        this.num = random.nextInt(12);

        // randomly assigns a new pentomino to array pentomino
        this.pentomino = PentominoBuilder.getBasicDatabase(num);

        // starting positions on field
        this.x = 1;
        this.y = 0;

        numberOfBlocks++;
        blockNum = numberOfBlocks;
    }

    // for the BestSequence, so we can cherry-pick the specific pentomino
    // block/shape
    public TetrisBlock(int tetNum) {
        this.num = tetNum;

        // assigns a designated (num) pentomino to array pentomino
        this.pentomino = PentominoBuilder.getBasicDatabase(num);

        // starting positions on field
        this.x = 1;
        this.y = 0;

        numberOfBlocks++;
        blockNum = numberOfBlocks;
    }

    // for the Bot so we can create copies of the TetrisBlock shape
    public TetrisBlock(TetrisBlock otherBlock) {
        this.num = otherBlock.num;
        this.pentomino = deepCopyPentomino(otherBlock.pentomino); // deep copy for refrence types
        this.x = otherBlock.x;
        this.y = otherBlock.y;
    }

    /**
     * copying refrences so it wont affect the original array if changes are mad to
     * the copy. eg. rotations and movement
     * 
     * @param originalPentomino the original pentomino shape which will be copied
     * @return a deep copy of the original array.
     */
    private int[][] deepCopyPentomino(int[][] originalPentomino) {
        // checks if the original array is null
        if (originalPentomino == null) {
            return null; // returns null if empty
        }
        // initialize a new array with the same length of the original
        int[][] copy = new int[originalPentomino.length][];
        for (int i = 0; i < originalPentomino.length; i++) {
            copy[i] = Arrays.copyOf(originalPentomino[i], originalPentomino[i].length);
        }
        return copy; // returns the deep copied array
    }

    public static void setNumberOfBlockToZero() {
        TetrisBlock.numberOfBlocks = 0;
    }

    public int[][] getShape() {
        return pentomino;
    }

    public int getNum() {
        return num;
    }

    public int getHeight() {
        return pentomino.length;
    }

    public int getWidth() {
        return pentomino[0].length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveDown() {
        y++;
    }

    public void moveUp() {
        y--;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public int getBottomEdge() {
        return y + getHeight();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentRotation() {
        return currentRotation;
    }

    public int getSpecificBlockNumber() {
        return blockNum;
    }

    public static int getTotalBlockNumber() {
        return numberOfBlocks;
    }

    public void rotate() {
        currentRotation = (currentRotation + 1) % 4;

        // the dimensions of the current shape
        int M = pentomino.length;
        int N = pentomino[0].length;

        int[][] rotated = new int[N][M]; // create a new array to hold the rotated piece

        // transpose and then reverse rows to rotate 90 degrees clockwise
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                rotated[c][M - 1 - r] = pentomino[r][c];
            }
        }

        pentomino = rotated; // set the rotated piece to be the current shape
    }

    public void rotateBack() {
        currentRotation = (currentRotation - 1 + 4) % 4;

        // get the dimensions of the current shape
        int M = pentomino.length;
        int N = pentomino[0].length;

        int[][] rotated = new int[N][M]; // create a new array to hold the rotated piece

        // transpose and then reverse columns to rotate 90 degrees counterclockwise
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                rotated[N - 1 - c][r] = pentomino[r][c];
            }
        }

        pentomino = rotated; // set the rotated piece to be the current shape
    }

    // for debugging reasons to see what new pentomino is displayed on the field
    public String getShapeLetter() {
        switch (getNum()) { // num
            case 1:
                return "I";
            case 2:
                return "Z";
            case 3:
                return "T";
            case 4:
                return "U";
            case 5:
                return "V";
            case 6:
                return "W";
            case 7:
                return "Y";
            case 8:
                return "L";
            case 9:
                return "P";
            case 10:
                return "N";
            case 11:
                return "F";
            case 12:
                return "X";
            default:
                return "X";
        }
    }

    public String toString() {
        return "#: " + getSpecificBlockNumber() + ". Letter: " + getShapeLetter() + ". Num: " + getNum()
                + ". Coordinate: (" + getX() + ", " + getY() + ")";
    }
}