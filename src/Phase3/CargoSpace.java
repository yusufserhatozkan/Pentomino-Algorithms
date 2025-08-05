package Phase3;

public class CargoSpace {
    protected static final int UNIT_SIZE = 1; // 0.5m represented as 1 unit
    private static final int EMPTY = -1;
    public static final int DEPTH = 33;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 8;
    private int lengthUnits; // 33 units for 16.5m X
    private int widthUnits; // 5 units for 2.5m Y
    private int heightUnits; // 8 units for 4.0m Z
    private static int[][][] space; // the cargo space where pentominos are placed in

    static { // static block
        initializeSpace(); // initializes the 3D area with -1's
    }

    /**
     * This initializes the space array with empty space (-1's)
     */
    public static void initializeSpace() {
        space = new int[DEPTH][WIDTH][HEIGHT]; // initializes ths space array thats empty but will be filled with -1's
        for (int i = 0; i < DEPTH; i++) { // depth
            for (int j = 0; j < WIDTH; j++) { // width
                for (int k = 0; k < HEIGHT; k++) { // height
                    space[i][j][k] = EMPTY; // Initialize all values to -1
                }
            }
        }
    }

    /**
     * The constructor for CargoSpace
     * initializes the length, width, and height
     */
    public CargoSpace() {
        this.lengthUnits = DEPTH; // length in units
        this.widthUnits = WIDTH; // width in units
        this.heightUnits = HEIGHT; // height in units
    }

    private boolean isolated(int[][][] cargoSpace, Pentomino pentomino, int x, int y, int z) {
        int numIsolation = 0;

        switch (cargoSpace[x][y][z]) { // 1
            case 1:
                if (cargoSpace[x + 1][y][z] == 1 && cargoSpace[x - 1][y][z] == 1 && cargoSpace[x][y + 1][z] == 1
                        && cargoSpace[x][y - 1][z] == 1 && cargoSpace[x][y][z + 1] == 1
                        && cargoSpace[x][y][z - 1] == 1) {
                    return true;
                }

            default:
                return false;
        }
    }

    private boolean isolated2(int[][][] cargoSpace, Pentomino pentomino, int x, int y, int z) {
        if (cargoSpace[x][y][z] == EMPTY) {
            switch (cargoSpace[x][y][z]) { // 1
                case 1:
                    if (cargoSpace[x + 1][y][z] == 1 && cargoSpace[x - 1][y][z] == 1 && cargoSpace[x][y + 1][z] == 1 &&
                            cargoSpace[x][y - 1][z] == 1 && cargoSpace[x][y][z + 1] == 1
                            && cargoSpace[x][y][z - 1] == 1) {
                        return true;
                    }
                case 2: // horizontal 2 empty spaces
                    if (cargoSpace[x + 1][y][z] == EMPTY &&
                            cargoSpace[x - 1][y][z] == 1 && cargoSpace[x + 2][y][z] == 1 &&
                            cargoSpace[x][y + 1][z] == 1 && cargoSpace[x + 1][y + 1][z] == 1
                            && cargoSpace[x][y - 1][z] == 1 &&
                            cargoSpace[x + 1][y - 1][z] == 1 && cargoSpace[x][y][z + 1] == 1
                            && cargoSpace[x][y][z - 1] == 1 &&
                            cargoSpace[x + 1][y][z + 1] == 1 && cargoSpace[x + 1][y][z - 1] == 1) {
                        return true;
                    }
                case 3: // vertical 2 empty spaces
                    if (cargoSpace[x][y - 1][z] == EMPTY &&
                            cargoSpace[x][y + 1][z] == 1 && cargoSpace[x][y - 2][z] == 1 &&
                            cargoSpace[x - 1][y][z] == 1 && cargoSpace[x + 1][y][z] == 1
                            && cargoSpace[x - 1][y - 1][z] == 1 &&
                            cargoSpace[x + 1][y - 1][z] == 1 && cargoSpace[x][y][z - 1] == 1
                            && cargoSpace[x - 1][y - 1][z] == 1 &&
                            cargoSpace[x][y][z + 1] == 1 && cargoSpace[x][y - 1][z + 1] == 1) {
                        return true;
                    }
                case 4: // horizontal 4 zeros
                    if (cargoSpace[x + 1][y][z] == EMPTY && cargoSpace[x][y][z - 1] == EMPTY
                            && cargoSpace[x + 1][y][z - 1] == EMPTY &&
                            cargoSpace[x][y + 1][z] == 1 && cargoSpace[x][y - 1][z] == 1
                            && cargoSpace[x + 1][y + 1][z] == 1 &&
                            cargoSpace[x + 1][y - 1][z] == 1 && cargoSpace[x][y + 1][z + 1] == 1
                            && cargoSpace[x][y - 1][z - 1] == 1 &&
                            cargoSpace[x + 1][y + 1][z + 1] == 1 && cargoSpace[x + 1][y + 1][z - 1] == 1
                            && cargoSpace[x + 2][y][z] == 1 &&
                            cargoSpace[x - 1][y][z] == 1 && cargoSpace[x + 2][y][z - 1] == 1
                            && cargoSpace[x - 1][y][z - 1] == 1 &&
                            cargoSpace[x][y][z + 1] == 1 && cargoSpace[x][y][z - 2] == 1
                            && cargoSpace[x + 1][y][z + 1] == 1 &&
                            cargoSpace[x + 1][y][z - 2] == 1) {
                        return true;
                    }

                case 5: // vertical 4 zeros
                    if (cargoSpace[x + 1][y][z] == EMPTY && cargoSpace[x][y - 1][z] == EMPTY
                            && cargoSpace[x + 1][y - 1][z] == EMPTY &&
                            cargoSpace[x - 1][y][z] == 1 && cargoSpace[x + 2][y][z] == 1
                            && cargoSpace[x - 1][y - 1][z] == 1 &&
                            cargoSpace[x + 2][y - 1][z] == 1 && cargoSpace[x][y + 1][z] == 1
                            && cargoSpace[x][y - 2][z] == 1 &&
                            cargoSpace[x + 1][y + 1][z] == 1 && cargoSpace[x + 1][y - 2][z] == 1
                            && cargoSpace[x][y][z + 1] == 1 &&
                            cargoSpace[x + 1][y][z + 1] == 1 && cargoSpace[x][y - 1][z + 1] == 1
                            && cargoSpace[x + 1][y - 1][z + 1] == 1 &&
                            cargoSpace[x][y][z - 1] == 1 && cargoSpace[x + 1][y][z - 1] == 1
                            && cargoSpace[x][y - 1][z - 1] == 1 &&
                            cargoSpace[x + 1][y - 1][z - 1] == 1) {
                        return true;
                    }

                case 6: // vertical 3 zeros
                    if (cargoSpace[x][y - 1][z] == EMPTY && cargoSpace[x][y - 2][z] == EMPTY &&
                            cargoSpace[x + 1][y][z] == 1 && cargoSpace[x - 1][y][z] == 1
                            && cargoSpace[x + 1][y - 1][z] == 1 &&
                            cargoSpace[x - 1][y - 1][z] == 1 && cargoSpace[x + 1][y - 2][z] == 1
                            && cargoSpace[x - 1][y - 2][z] == 1 &&
                            cargoSpace[x][y + 1][z] == 1 && cargoSpace[x][y - 3][z] == 1 &&
                            cargoSpace[x][y][z - 1] == 1 && cargoSpace[x][y][z + 1] == 1
                            && cargoSpace[x][y - 1][z - 1] == 1 &&
                            cargoSpace[x][y - 1][z + 1] == 1 && cargoSpace[x][y - 2][z - 1] == 1
                            && cargoSpace[x][y - 2][z + 1] == 1) {
                        return true;
                    }

                case 7: // horizontal 3 zeros
                    if (cargoSpace[x - 1][y][z] == EMPTY && cargoSpace[x + 3][y][z] == EMPTY &&
                            cargoSpace[x][y - 1][z] == 1 && cargoSpace[x][y + 1][z] == 1
                            && cargoSpace[x + 1][y - 1][z] == 1 &&
                            cargoSpace[x + 1][y + 1][z] == 1 && cargoSpace[x + 2][y - 1][z] == 1
                            && cargoSpace[x + 2][y + 1][z] == 1 &&
                            cargoSpace[x][y][z + 1] == 1 && cargoSpace[x][y][z - 1] == 1
                            && cargoSpace[x + 1][y][z - 1] == 1 &&
                            cargoSpace[x + 1][y][z + 1] == 1 && cargoSpace[x + 2][y][z - 1] == 1
                            && cargoSpace[x + 2][y][z + 1] == 1) {
                        return true;
                    }

                case 8: // diagonal 2 zeros (z axis)
                    if (cargoSpace[x][y][z + 1] == EMPTY &&
                            cargoSpace[x + 1][y][z] == 1 && cargoSpace[x - 1][y][z] == 1
                            && cargoSpace[x + 1][y][z + 1] == 1 &&
                            cargoSpace[x - 1][y][z + 1] == 1 && cargoSpace[x][y - 1][z] == 1
                            && cargoSpace[x][y + 1][z] == 1 &&
                            cargoSpace[x][y - 1][z + 1] == 1 && cargoSpace[x][y + 1][z + 1] == 1) {
                        return true;
                    }

                case 9: // diagonal 3 zeros (z axis)
                    if (cargoSpace[x][y][z + 1] == EMPTY && cargoSpace[x][y][z + 2] == EMPTY &&
                            cargoSpace[x][y][z - 1] == 1 && cargoSpace[x][y][z + 3] == 1 &&
                            cargoSpace[x + 1][y][z] == 1 && cargoSpace[x - 1][y][z] == 1
                            && cargoSpace[x + 1][y][z + 1] == 1 &&
                            cargoSpace[x - 1][y][z + 1] == 1 && cargoSpace[x + 1][y][z + 2] == 1
                            && cargoSpace[x - 1][y][z + 2] == 1 &&
                            cargoSpace[x][y + 1][z] == 1 && cargoSpace[x][y - 1][z] == 1
                            && cargoSpace[x][y + 1][z + 1] == 1 &&
                            cargoSpace[x][y - 1][z + 1] == 1 && cargoSpace[x][y + 1][z + 2] == 1
                            && cargoSpace[x][y - 1][z + 2] == 1) {
                        return true;
                    }

                case 10: // diagonal 4 zeros (z axis)
                    if (cargoSpace[x][y][z + 1] == EMPTY && cargoSpace[x][y + 1][z] == EMPTY
                            && cargoSpace[x][y + 1][z + 1] == EMPTY &&
                            cargoSpace[x + 1][y][z] == 1 && cargoSpace[x - 1][y][z] == 1
                            && cargoSpace[x + 1][y + 1][z] == 1 &&
                            cargoSpace[x - 1][y + 1][z] == 1 && cargoSpace[x + 1][y][z + 1] == 1
                            && cargoSpace[x - 1][y][z + 1] == 1 &&
                            cargoSpace[x + 1][y + 1][z + 1] == 1 && cargoSpace[x - 1][y + 1][z + 1] == 1 &&
                            cargoSpace[x][y][z - 1] == 1 && cargoSpace[x][y][z + 2] == 1
                            && cargoSpace[x][y + 1][z - 1] == 1 &&
                            cargoSpace[x][y][z + 2] == 1 && cargoSpace[x][y - 1][z] == 1
                            && cargoSpace[x][y - 1][z + 1] == 1 &&
                            cargoSpace[x][y + 2][z] == 1 && cargoSpace[x][y + 2][z + 1] == 1) {
                        return true;
                    }

                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * Removes a pentomino from the cargo space.
     *
     * @param pentomino The pentomino to be removed.
     * @param startX    The starting X-coordinate of the pentomino.
     * @param startY    The starting Y-coordinate of the pentomino.
     * @param startZ    The starting Z-coordinate of the pentomino.
     */
    public static void removePentomino(Pentomino pentomino, int startX, int startY, int startZ) {
        int[][][] shape = pentomino.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    if (shape[x][y][z] != EMPTY) {
                        // Check bounds before removing the pentomino part
                        if (isWithinBounds(startX + x, startY + y, startZ + z)) {
                            space[startX + x][startY + y][startZ + z] = EMPTY;
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the given coordinates are within the bounds of the cargo space.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @param z The Z-coordinate.
     * @return true if the coordinates are within bounds, false otherwise.
     */
    private static boolean isWithinBounds(int x, int y, int z) {
        return x >= 0 && x < DEPTH && y >= 0 && y < WIDTH && z >= 0 && z < HEIGHT;
    }

    /**
     * Checks if the cargo space is completely filled with no empty spaces.
     * 
     * @return true if there are no empty spaces, false otherwise.
     */
    public boolean isFull() {
        for (int i = 0; i < DEPTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                for (int k = 0; k < HEIGHT; k++) {
                    if (space[i][j][k] == EMPTY) {
                        return false; // Found an empty space, so it's not full
                    }
                }
            }
        }
        return true; // No empty spaces found, so it's full
    }

    public int getDepth() {
        return lengthUnits;
    }

    public int getHeight() {
        return heightUnits;
    }

    public int getWidth() {
        return widthUnits;
    }

    public int[][][] getSpace() {
        return space;
    }

}
