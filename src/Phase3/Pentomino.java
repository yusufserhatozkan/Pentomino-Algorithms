package Phase3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pentomino {
    private static final int EMPTY = -1;
    private static final int DEPTH = 33;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 8;
    private char type; // L, P, or T
    private int amount; // the amount of this pentomino that will be used
    private int value;
    private int xPos; // x position
    private int yPos; // y position
    private int zPos; // z position
    private int rotationNum; // rotation number
    private int[][][] shape; // the array of the 3D letter/pentomino
    private int copyNum; // the copy/replica number
    private List<Pentomino> rotations; // Stores all unique rotations
    private boolean used = false;

    private final int[][][] shapeL = { // 1
            {
                    { 1, EMPTY, EMPTY },
            },
            {
                    { 1, EMPTY, EMPTY },

            },
            {
                    { 1, EMPTY, EMPTY },

            },
            {
                    { 1, 1, EMPTY },
            },
    };

    private final int[][][] shapeP = { // 2
            {
                    { 2, 2 },
            },
            {
                    { 2, 2 },
            },
            {
                    { 2, EMPTY }
            },
    };

    private final int[][][] shapeT = { // 3
            {
                    { 3, 3, 3 },
            },
            {
                    { EMPTY, 3, EMPTY },
            },
            {
                    { EMPTY, 3, EMPTY },
            },

    };

    private final static int[][][] parcelA = fillParcel(2, 2, 4, 4); // 4
    private final static int[][][] parcelB = fillParcel(2, 3, 4, 4); // 5
    private final static int[][][] parcelC = fillParcel(3, 3, 3, 4); // 6

    private static int[][][] fillParcel(int x, int y, int z, int num) {
        int[][][] array = new int[x][y][z];
        for (int i = 0; i < array.length; i++) { // depth
            for (int j = 0; j < array[i].length; j++) { // width
                for (int k = 0; k < array[i][j].length; k++) { // height
                    array[i][j][k] = num; // Initialize all values with the x value
                }
            }
        }
        return array;
    }

    public Pentomino(char type, int copyNum, boolean generateRotations) {
        this.type = type;
        this.amount = 1;
        this.copyNum = copyNum;
        this.xPos = 0;
        this.yPos = 0;
        this.zPos = 0;
        this.rotationNum = 0;
        rotations = new ArrayList<>();
        this.used = false;
        initializeShapeAndValue();

        if (generateRotations) {
            generateAllRotations();
        }
    }

    private void initializeShapeAndValue() {
        switch (type) {
            case 'L':
                this.shape = shapeL;
                this.value = 10;
                break;
            case 'P':
                this.shape = shapeP;
                this.value = 10;
                break;
            case 'T':
                this.shape = shapeT;
                this.value = 10;
                break;
            case 'A':
                this.shape = parcelA;
                this.value = 10;
                break;
            case 'B':
                this.shape = parcelB;
                this.value = 10;
                break;
            case 'C':
                this.shape = parcelC;
                this.value = 10;
                break;
            default:
                throw new IllegalArgumentException("Invalid pentomino type: " + type);
        }
    }

    private void generateAllRotations() {
        Set<String> uniqueShapes = new HashSet<>();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    Pentomino rotatedPent = new Pentomino(this.type, this.copyNum, false); // False to prevent recursion
                    for (int i = 0; i < x; i++)
                        rotatedPent.rotateX();
                    for (int i = 0; i < y; i++)
                        rotatedPent.rotateY();
                    for (int i = 0; i < z; i++)
                        rotatedPent.rotateZ();

                    String shapeString = Arrays.deepToString(rotatedPent.getShape());
                    if (uniqueShapes.add(shapeString)) {
                        rotatedPent.setRotationNum(rotations.size());
                        rotations.add(rotatedPent);
                    }
                }
            }
        }
    }

    /**
     * Creates a deep copy of a 3D array (shape).
     * 
     * @param original Original 3D array.
     * @return Deep copy of the original array.
     */
    private int[][][] copyShape(int[][][] original) {
        int[][][] copy = new int[original.length][][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new int[original[i].length][];
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = original[i][j].clone();
            }
        }
        return copy;
    }

    /**
     * Rotates the shape around the X-axis
     */
    public void rotateX() {
        int[][][] newShape = new int[shape.length][shape[0][0].length][shape[0].length];

        for (int x = 0; x < shape.length; x++) {
            for (int z = 0; z < shape[x][0].length; z++) {
                for (int y = 0; y < shape[x].length; y++) {
                    newShape[x][z][y] = shape[x][y][shape[x][0].length - 1 - z];
                }
            }
        }
        shape = newShape;
    }

    /**
     * Rotates the shape around the Y-axis
     */
    public void rotateY() {
        int[][][] newShape = new int[shape[0][0].length][shape.length][shape[0].length];

        for (int z = 0; z < shape[0][0].length; z++) {
            for (int x = 0; x < shape.length; x++) {
                for (int y = 0; y < shape[x].length; y++) {
                    newShape[z][x][y] = shape[shape.length - 1 - x][y][z];
                }
            }
        }
        shape = newShape;
    }

    /**
     * Rotates the shape around the Z-axis
     */
    public void rotateZ() {
        int[][][] newShape = new int[shape[0].length][shape[0][0].length][shape.length];

        for (int y = 0; y < shape[0].length; y++) {
            for (int x = 0; x < shape.length; x++) {
                for (int z = 0; z < shape[0][0].length; z++) {
                    newShape[y][z][x] = shape[x][shape[0].length - 1 - y][z];
                }
            }
        }
        shape = newShape;
    }

    public int[][][] getShape() {
        return shape;
    }

    public char getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int x) {
        this.xPos = x;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int y) {
        this.yPos = y;
    }

    public int getZPos() {
        return zPos;
    }

    public void setZPos(int z) {
        this.zPos = z;
    }

    public int getCopyNum() {
        return copyNum;
    }

    public void setCopyNum(int newCopyNum) {
        this.copyNum = newCopyNum;
    }

    public int getRotationNum() {
        return rotationNum;
    }

    public void setRotationNum(int newRotationNum) {
        this.rotationNum = newRotationNum;
    }

    public void setShape(int[][][] newShape) {
        shape = newShape;
    }

    public List<Pentomino> getRotations() {
        return rotations;
    }

    public boolean getUsed(){
        return used;
    }

    public void setUsed(boolean used){
        this.used = used;
    }

    public String toString() {
        return "X: " + getXPos() + ". Y: " + getYPos() + ". Z: " + getZPos() + ". Rotation: " + rotationNum;
    }

}

class PentominoCollection {
    private Pentomino mainPent;
    private char type;
    private int quantity;
    private ArrayList<Pentomino> pentominos;

    public PentominoCollection(char type, int quantity) {
        this.type = type;
        this.quantity = quantity;
        pentominos = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            pentominos.add(new Pentomino(type, i, true)); // true to generate rotations
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public ArrayList<Pentomino> getPentominos() {
        return pentominos;
    }

}

class MainTester {
    public static void main(String[] args) {
        int numL = 10;

        PentominoCollection pentLCollection = new PentominoCollection('L', numL);

        System.out.println(pentLCollection.getPentominos().size());
        System.out.println(pentLCollection.getPentominos().get(1).getRotations().size());

        // Test prints
        for (Pentomino pent : pentLCollection.getPentominos()) {
            System.out.println("Pentomino L copy number: " + pent.getCopyNum() + " has " + pent.getRotations().size()
                    + " unique rotations.");
            // Optional: Print details of each rotation
            for (Pentomino rotation : pent.getRotations()) {
                // Print details of each rotation if needed
            }
        }
    }
}
