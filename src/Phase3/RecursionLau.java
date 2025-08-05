package Phase3;

import java.util.ArrayList;

public class RecursionLau {
    private static final int EMPTY = -1; // empty space
    private static final int DEPTH = 33; // x
    private static final int WIDTH = 5; // y
    private static final int HEIGHT = 8; // z
    private ArrayList<Pentomino> backTrackArray; // stores the pentominos when they are placed
    private CargoSpace cargoSpace; // the cargoSpace where pentominos are inserted into
    private int usedPent1; // how many pentomino 1 are used
    private int usedPent2; // how many pentomino 2 are used
    private int usedPent3; // how many pentomino 3 are used
    private int availablePent1; // maximum avaliable amount of pentominos 1
    private int availablePent2; // maximum avaliable amount of pentominos 2
    private int availablePent3; // maximum avaliable amount of pentominos 3
    private int counter = availablePent1 + availablePent2 + availablePent3;
    private ArrayList<Pentomino> allPentominos1; // Stores all pentominos with each having many rotations
    private ArrayList<Pentomino> allPentominos2; // Stores all pentominos with each having many rotations
    private ArrayList<Pentomino> allPentominos3; // Stores all pentominos with each having many rotations

    public RecursionLau(ArrayList<Pentomino> pentominos1, ArrayList<Pentomino> pentominos2,
            ArrayList<Pentomino> pentominos3) {
        this.cargoSpace = new CargoSpace();
        this.backTrackArray = new ArrayList<>();
        this.allPentominos1 = pentominos1; // 1
        this.allPentominos2 = pentominos2; // 2
        this.allPentominos3 = pentominos3; // 3
        this.availablePent1 = pentominos1.size();
        this.availablePent2 = pentominos2.size();
        this.availablePent3 = pentominos3.size();
        this.counter = availablePent1 + availablePent2 + availablePent3;
        // System.out.println(" THE SIZE OF ARRAY 1: " + availablePent1);
        // System.out.println(" THE SIZE OF ARRAY 2: " + availablePent2);
        // System.out.println(" THE SIZE OF ARRAY 3: " + availablePent3);
        System.out.println(" CAN USE: " + counter);
    }

    public boolean toNextPentomino(int xCoor, int yCoor, int zCoor, int[][][] cargoShape, int arrayNum) {
        if (isCargoSpaceFull(cargoShape) || counter == 0) {
            System.out.println("Solution found");
            return true; // Base case: Cargo space is full or no pentominos left to place
        }

        if (insertPentomino2(arrayNum, xCoor, yCoor, zCoor, cargoShape)) {
            counter--;
            // Find the next empty cell

            for (int x = xCoor; x < DEPTH; x++) {
                for (int y = (x == xCoor ? yCoor : 0); y < WIDTH; y++) {
                    for (int z = (x == xCoor && y == yCoor ? zCoor : 0); z < HEIGHT; z++) {
                        if (cargoShape[x][y][z] == EMPTY) {
                            int[] nextEmptySpace = findNextEmptySpace(cargoShape, xCoor, yCoor, zCoor);
                            if (nextEmptySpace != null) {
                                if (toNextPentomino(nextEmptySpace[0], nextEmptySpace[1], nextEmptySpace[2], cargoShape,
                                        1)) {
                                    return true;
                                }
                            }
                            break; // Exit if backtracking
                        }
                    }
                }
            }
        }

        // Backtrack
        if (!backTrackArray.isEmpty()) {
            Pentomino lastPlaced = backTrackArray.remove(backTrackArray.size() - 1);
            removePentomino(lastPlaced, cargoSpace);
            counter++;
        }
        return false; // No valid configuration found
    }

    // New method to find the next empty space after a given position

    private int[] findNextEmptySpace(int[][][] cargoShape, int startX, int startY, int startZ) {
        for (int x = startX; x < DEPTH; x++) {
            int yStart = (x == startX) ? startY : 0;
            for (int y = yStart; y < WIDTH; y++) {
                int zStart = (x == startX && y == startY) ? startZ : 0;
                for (int z = zStart; z < HEIGHT; z++) {
                    if (cargoShape[x][y][z] == EMPTY) {
                        return new int[] { x, y, z };
                    }
                }
            }
        }
        return null; // No empty space found
    }

    /**
     * Checks if cargo is full. Returns false if there is a -1 in the cargo.
     * 
     * @param cargoShape is the array of the current state of the cargo
     * @return
     */
    private boolean isCargoSpaceFull(int[][][] cargoShape) {
        for (int x = 0; x < DEPTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                for (int z = 0; z < HEIGHT; z++) {
                    if (cargoShape[x][y][z] == EMPTY) {
                        return false; // Found an empty space
                    }
                }
            }
        }
        return true; // No empty spaces found, cargo space is full
    }

    public void removePentomino(Pentomino pentomino, CargoSpace cargoSpace) {
        int x = pentomino.getXPos();
        int y = pentomino.getYPos();
        int z = pentomino.getZPos();
        int[][][] shape = pentomino.getShape();
        for (int dx = 0; dx < shape.length; dx++) {
            for (int dy = 0; dy < shape[dx].length; dy++) {
                for (int dz = 0; dz < shape[dx][dy].length; dz++) {
                    if (shape[dx][dy][dz] != EMPTY) {
                        cargoSpace.getSpace()[x + dx][y + dy][z + dz] = EMPTY;
                    }
                }
            }
        }
    }

    /**
     * Checks if the pentomino block can be inserted at a specific x, y, z position
     * in the cargo
     * 
     * @param pentomino  is the pentomino we want to try and insert into cargo
     * @param startX     is the x position the pentomino tries to be inserted into
     * @param startY     is the y position the pentomino tries to be inserted into
     * @param startZ     is the z position the pentomino tries to be inserted into
     * @param cargoShape is the cargos current state
     * @return
     */
    public static boolean canInsert(Pentomino pentomino, int startX, int startY, int startZ, int[][][] cargoShape) {
        int[][][] shape = pentomino.getShape();
        // Check if the pentomino can be placed
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    // Check boundaries
                    if (startX + x >= DEPTH || startY + y >= WIDTH || startZ + z >= HEIGHT) {
                        return false; // Out of bounds
                    }
                    // Check for overlap
                    if (shape[x][y][z] != EMPTY && cargoShape[startX + x][startY + y][startZ + z] != EMPTY) {
                        return false; // Space already occupied
                    }
                }
            }
        }
        return true;
    }

    public boolean insertPentomino2(int arrayNum, int startX, int startY, int startZ, int[][][] cargoShape) {
        // Loop through each array (1 to 3)
        for (int num = 0; num < 3; num++, arrayNum = (arrayNum % 3) + 1) {
            ArrayList<Pentomino> currentList = getSpecificArray(arrayNum);

            if (getAvailablePentCount(arrayNum) <= 0) {
                continue; // Skip to next array if no pentominos are available in the current array
            }

            for (int pentIndex = 0; pentIndex < currentList.size(); pentIndex++) {
                Pentomino currentPent = currentList.get(pentIndex);

                for (int rotationIndex = 0; rotationIndex < currentPent.getRotations().size(); rotationIndex++) {
                    Pentomino rotatedPent = currentPent.getRotations().get(rotationIndex);

                    if (canInsert(rotatedPent, startX, startY, startZ, cargoShape)) {
                        placePentomino(rotatedPent, startX, startY, startZ, cargoShape); // Place the pentomino
                        decrementAvailablePent(arrayNum); // Decrement the available pentomino count
                        backTrackArray.add(rotatedPent);
                        return true; // Successfully inserted
                    }
                }
            }
        }
        return false; // No placement found in any array
    }

    /**
     * Place the pentomino into specific x, y, z position into the cargo
     * 
     * @param pentomino  is the pentomino we want to place into the cargo
     * @param startX     is the x position the pentomino will be placed into
     * @param startY     is the y position the pentomino will be placed into
     * @param startZ     is the z position the pentomino will be placed into
     * @param cargoShape is the cargos current state
     */
    private void placePentomino(Pentomino pentomino, int startX, int startY, int startZ, int[][][] cargoShape) {
        int[][][] shape = pentomino.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    if (shape[x][y][z] != EMPTY) {
                        cargoShape[startX + x][startY + y][startZ + z] = shape[x][y][z];
                    }
                }
            }
        }
    }

    private void decrementAvailablePent(int arrayNum) {
        // Decrement the appropriate availablePent count based on arrayNum
        if (arrayNum == 1) {
            availablePent1--;
            usedPent1++;
        } else if (arrayNum == 2) {
            availablePent2--;
            usedPent2++;
        } else if (arrayNum == 3) {
            availablePent3--;
            usedPent3++;
        }
    }

    /**
     * Gives us the specific rotation of the specific copy of a pentomino
     * 
     * @param whichArray either 1, 2, 3
     * @param copyNum    what copy number to get
     * @param rotNum     what rotation number to get
     * @return the Pentomino of a specific copy and rotation
     */
    public Pentomino getCopyAndRot(int whichArray, int copyNum, int rotNum) {
        return getSpecificArray(whichArray).get(copyNum).getRotations().get(rotNum);
    }

    public ArrayList<Pentomino> getSpecificArray(int arrayNum) {
        switch (arrayNum) {
            case 1:
                return allPentominos1;
            case 2:
                return allPentominos2;
            case 3:
                return allPentominos3;
            default:
                throw new IllegalArgumentException("INVALID ARRAY NUMBER: " + arrayNum);
        }
    }

    public int getSpecificArray(Pentomino pent) {
        switch (pent.getType()) {
            case 'L':
            case 'A':
                return 1;
            case 'T':
            case 'B':
                return 2;
            case 'P':
            case 'C':
                return 3;
            default:
                throw new IllegalArgumentException("INVALID ARRAY TYPE: " + pent.getType());
        }
    }

    private int getAvailablePentCount(int arrayNum) {
        switch (arrayNum) {
            case 1:
                return availablePent1;
            case 2:
                return availablePent2;
            case 3:
                return availablePent3;
            default:
                throw new IllegalArgumentException("Invalid array number: " + arrayNum);
        }
    }

    public int getTotalUsed() {
        return usedPent1 + usedPent2 + usedPent3;
    }

    public ArrayList<Pentomino> getBackTrackArray() {
        return backTrackArray;
    }

    public String toString() {
        return "Total used: " + getTotalUsed() + ". 1's: " + usedPent1 + ". 2's: " + usedPent2 + ". 3's: " + usedPent3;
    }
}