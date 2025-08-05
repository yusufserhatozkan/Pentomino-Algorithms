package Phase3;

import java.util.ArrayList;
import java.util.Arrays;

public class RecursionLau2 {
    private static final int EMPTY = -1; // empty space
    private static final int DEPTH = 33; // x
    private static final int WIDTH = 5; // y
    private static final int HEIGHT = 8; // z

    private CargoSpace cargoSpace; // cargospace
    private ArrayList<Pentomino> allCopiesOfPent1; // getCopyOfPentArray(1)
    private ArrayList<Pentomino> allCopiesOfPent2; // getCopyOfPentArray(2)
    private ArrayList<Pentomino> allCopiesOfPent3; // getCopyOfPentArray(3)
    private int copyAmountPent1; // amount of pentomino 1's we can use
    private int copyAmountPent2; // amount of pentomino 2's we can use
    private int copyAmountPent3; // amount of pentomino 3's we can use

    private int usedPent1 = 0; // counter to keep track how many we used of penttomino 1
    private int usedPent2 = 0; // counter to keep track how many we used of penttomino 2
    private int usedPent3 = 0; // counter to keep track how many we used of penttomino 3
    private int counter = 0; // test counter

    private int currentArray = 1; // keep track of which currentArray we are in right now (1, 2, or 3)
    private int currentDuplicate = 0; // keep track of which duplicate we are in right now
    private int currentRotation = 0; // keep track of which rotation we are in right now

    private int sucessfullInsert = 0;

    public RecursionLau2(ArrayList<Pentomino> allCopiesOfPentomino1, ArrayList<Pentomino> allCopiesOfPentomino2,
            ArrayList<Pentomino> allCopiesOfPentomino3) {
        this.cargoSpace = new CargoSpace();
        this.allCopiesOfPent1 = allCopiesOfPentomino1;
        this.allCopiesOfPent2 = allCopiesOfPentomino2;
        this.allCopiesOfPent3 = allCopiesOfPentomino3;
        this.copyAmountPent1 = allCopiesOfPentomino1.size();
        this.copyAmountPent2 = allCopiesOfPentomino2.size();
        this.copyAmountPent3 = allCopiesOfPentomino3.size();
    }

    /**
     * Getter method that gives us the specific rotation of the specific copy of a
     * pentomino
     * 
     * @param whichArray   either 1, 2, 3
     * @param duplicateNum what duplicate of the array pentomino to get
     * @param rotNum       what rotation number to get
     * @return the Pentomino of a specific copy and rotation
     */
    private Pentomino getCopyAndRot(int whichArray, int duplicateNum, int rotNum) {
        return getCopyOfPentArray(whichArray).get(duplicateNum).getRotations().get(rotNum);
    }

    /**
     * Getter method to get the arraylist of the specific pentominos.
     * These arraylist store an amount of idential pentominos, with each having all
     * possible rotations
     * 
     * @param whichArray enter either 1, 2, 3 (allCopiesOfPent1, allCopiesOfPent2,
     *                   allCopiesOfPent3)
     * @return the arraylist
     */
    private ArrayList<Pentomino> getCopyOfPentArray(int whichArray) {
        switch (whichArray) {
            case 1:
                return allCopiesOfPent1;
            case 2:
                return allCopiesOfPent2;
            case 3:
                return allCopiesOfPent3;
            default:
                throw new IllegalArgumentException("INVALID ARRAY NUMBER: " + whichArray);
        }
    }

    /**
     * Checks if the current pentomino was already used
     * 
     * @param pentomino that we want to insert into the cargo
     * @return true if used already, otherwise false
     */
    private boolean isPentominoUsed(Pentomino pentomino) {
        return pentomino.getUsed();
    }

    /**
     * Will mark the pentomino as used when we use it in the recursion
     * Adds 1 to the appropriate counter too (for debugging purposes)
     * 
     * @param pentomino that we will insert into the cargo
     */
    private void markPentominoAsUsed(Pentomino pentomino) {
        if (pentomino.getType() == 'L') {
            usedPent1++;
        }
        if (pentomino.getType() == 'T') {
            usedPent2++;
        }
        if (pentomino.getType() == 'P') {
            usedPent3++;
        }
        pentomino.setUsed(true);
    }

    /**
     * Will mark the pentomino as not used when we backtrack
     * Removes 1 to the appropriate counter too (for debugging purposes)
     * 
     * @param pentomino that we will extract from the cargo
     */
    private void markPentominoAsNotUsed(Pentomino pentomino) {
        if (pentomino.getType() == 'L') {
            usedPent1--;
        }
        if (pentomino.getType() == 'T') {
            usedPent2--;
        }
        if (pentomino.getType() == 'P') {
            usedPent3--;
        }
        pentomino.setUsed(false);
    }

    /**
     * Insert the pentomino piece into a specific x, y, z position.
     * First it checks what pentomino, duplicate, and rotation to be used next.
     * Then it inserts it
     * 
     * @param startX is the x position to insert the piece in
     * @param startY is the y position to insert the piece in
     * @param startZ is the z position to insert the piece in
     * @return true when it sucessfully insers the pentomino, otherwise false (no
     *         suitable pernomino found, meaning we got to backtrack)
     */
    public boolean insert(int startX, int startY, int startZ) {
        Pentomino fittingPentomino = checkNextRotationAndPentomino(startX, startY, startZ);

        if (fittingPentomino != null) {
            int[][][] shape = fittingPentomino.getShape();
            for (int x = 0; x < shape.length; x++) {
                for (int y = 0; y < shape[x].length; y++) {
                    for (int z = 0; z < shape[x][y].length; z++) {
                        if (shape[x][y][z] != EMPTY) {
                            cargoSpace.getSpace()[startX + x][startY + y][startZ + z] = shape[x][y][z];
                        }
                    }
                }
            }
            sucessfullInsert++;
            return true; // Successfully inserted the pentomino
        } else {
            return false; // No suitable pentomino was found
        }
    }

    /**
     * Searches for the next pentomino that can be placed in the cargo space at the
     * specified position.
     * It iterates through all the available pentominoes across all three arrays and
     * checks each unused pentomino and its rotations.
     * If a rotation fits at the specified position without overlapping or going out
     * of bounds, it marks the pentomino as used and returns the fitting rotation.
     * 
     * @param startX is the x position to insert the piece in
     * @param startY is the y position to insert the piece in
     * @param startZ is the z position to insert the piece in
     * @return The fitting rotation of a pentomino that can be placed at the
     *         specified position, or null if no suitable pentomino or rotation is
     *         found.
     */
    private Pentomino checkNextRotationAndPentomino(int startX, int startY, int startZ) {
        for (ArrayList<Pentomino> pentominoCopies : Arrays.asList(allCopiesOfPent1, allCopiesOfPent2,
                allCopiesOfPent3)) {
            for (Pentomino pentomino : pentominoCopies) {
                if (!pentomino.getUsed()) {
                    for (Pentomino rotation : pentomino.getRotations()) {
                        if (canInsert(rotation, startX, startY, startZ)) {
                            markPentominoAsUsed(pentomino);
                            return rotation; // Found a fitting pentomino
                        }
                    }
                }
            }
        }

        return null; // No fitting pentomino or rotation found
    }

    /**
     * Checks if we can insert the pentomino piece into the cargo at a specific x,
     * y, z position.
     * Checks for out of bound error and checks for overlapping
     * 
     * @param pentomino is the current piece we want to place into cargo
     * @param startX    is the x position to insert the piece in
     * @param startY    is the y position to insert the piece in
     * @param startZ    is the z position to insert the piece in
     * @return true if we can insert, otherwise false
     */
    private boolean canInsert(Pentomino pentomino, int startX, int startY, int startZ) {
        int[][][] shape = pentomino.getShape();
        int[][][] cargoShape = cargoSpace.getSpace();

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
        return true; // Can insert pentomino
    }

    /**
     * Checks if the cargo is full. Meaning that there are no -1's in the cargo
     * 
     * @return true if no -1's in cargo, otherwise false
     */
    private boolean isCargoSpaceFullyOccupied() {
        for (int x = 0; x < DEPTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                for (int z = 0; z < HEIGHT; z++) {
                    if (cargoSpace.getSpace()[x][y][z] == EMPTY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Places the pentomino at a specific x, y, z position.
     * 
     * @param pentomino is the current piece we want to place into cargo
     * @param startX    is the x position to insert the piece in
     * @param startY    is the y position to insert the piece in
     * @param startZ    is the z position to insert the piece in
     */
    private void placePentomino(Pentomino pentomino, int startX, int startY, int startZ) {
        int[][][] shape = pentomino.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    if (shape[x][y][z] != EMPTY) {
                        cargoSpace.getSpace()[startX + x][startY + y][startZ + z] = shape[x][y][z];
                    }
                }
            }
        }
    }

    /**
     * Removes the pentomino at a specific x, y, z position.
     * For backtracking purposes
     * 
     * @param pentomino is the current piece we want to place into cargo
     * @param startX    is the x position to extract the piece from
     * @param startY    is the y position to extract the piece from
     * @param startZ    is the z position to extract the piece from
     */
    private void removePentomino(Pentomino pentomino, int startX, int startY, int startZ) {
        int[][][] shape = pentomino.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                for (int z = 0; z < shape[x][y].length; z++) {
                    if (shape[x][y][z] != EMPTY) {
                        cargoSpace.getSpace()[startX + x][startY + y][startZ + z] = EMPTY;
                    }
                }
            }
        }
    }

    /**
     * Solves the problem of placing pentominoes in the cargo space. It recursively
     * tries to place pentominoes starting from a given array and position, and
     * backtracks if no suitable placement is found. The method uses a depth-first
     * search approach to fill the cargo space.
     * The method stops and returns true when the cargo space is fully occupied.
     * 
     * @param whichArray The index of the pentomino array from which to start the
     *                   placement (1, 2, or 3).
     * @param startX     is the x position to extract the piece from
     * @param startY     is the y position to extract the piece from
     * @param startZ     is the z position to extract the piece from
     * @return true if the cargo space is fully occupied, otherwise false.
     */
    public boolean solve(int whichArray, int startX, int startY, int startZ) {
        // Base case: Check if cargo space is fully occupied
        if (isCargoSpaceFullyOccupied() || counter == 100) {
            System.out.println("CARGO IS FULLY OCCUPIED");
            return true;
        }

        // Recursive step
        for (int array = whichArray; array <= 3; array++) {
            ArrayList<Pentomino> pentominoCopies = getCopyOfPentArray(array);
            for (Pentomino pentomino : pentominoCopies) {
                if (!isPentominoUsed(pentomino)) {
                    for (Pentomino rotation : pentomino.getRotations()) {
                        if (canInsert(rotation, startX, startY, startZ)) {
                            placePentomino(rotation, startX, startY, startZ);
                            markPentominoAsUsed(pentomino);

                            // Recursively try to place the next pentomino
                            if (solve(array, startX, startY, startZ)) {
                                counter++;
                                System.out.println("PLACING NEXT PENTOMINO");
                                return true;
                            }

                            // Backtrack
                            removePentomino(rotation, startX, startY, startZ);
                            markPentominoAsNotUsed(pentomino);
                        }
                    }
                }
            }
        }

        return false; // No solution found
    }

    /**
     * Gets the total sucessful insert (for debugging)
     * 
     * @return an int of how many pieces were placed into cargo
     */
    public int getSucessfullInsert() {
        return sucessfullInsert;
    }

    /**
     * For testing purposes, to see how many pentominos used and stuff.
     */
    public void getInformation() {
        System.out.println("Used Pentomino 1: " + usedPent1);
        System.out.println("Used Pentomino 2: " + usedPent2);
        System.out.println("Used Pentomino 3: " + usedPent3);

        System.out.println("Pentomino 1 Array: " + allCopiesOfPent1.size());
        System.out.println("Pentomino 2 Array: " + allCopiesOfPent2.size());
        System.out.println("Pentomino 3 Array: " + allCopiesOfPent3.size());

    }
}
