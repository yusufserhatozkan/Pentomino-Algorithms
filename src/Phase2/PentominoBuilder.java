package Phase2;

/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This class contains all the methods that you may need to start developing
 * your project together with the representation of the pentomino's pieces
 */
public class PentominoBuilder {

    // All basic pentominoes that will be rotated
    public static int[][][] basicDatabase = {
            {
                    // pentomino representation X 0
                    { 0, 1, 0 },
                    { 1, 1, 1 },
                    { 0, 1, 0 }
            },
            {
                    // pentomino representation I 1
                    { 1 },
                    { 1 },
                    { 1 },
                    { 1 },
                    { 1 }
            },
            {
                    // pentomino representation Z 2
                    { 0, 1, 1 },
                    { 0, 1, 0 },
                    { 1, 1, 0 }
            },
            {
                    // pentomino representation T 3
                    { 1, 1, 1 },
                    { 0, 1, 0 },
                    { 0, 1, 0 }
            },
            {
                    // pentomino representation U 4
                    { 1, 1 },
                    { 1, 0 },
                    { 1, 1 }
            },
            {
                    // pentomino representation V 5
                    { 1, 1, 1 },
                    { 1, 0, 0 },
                    { 1, 0, 0 }
            },
            {
                    // pentomino representation W 6
                    { 0, 0, 1 },
                    { 0, 1, 1 },
                    { 1, 1, 0 }
            },
            {
                    // pentomino representation Y 7
                    { 1, 0 },
                    { 1, 1 },
                    { 1, 0 },
                    { 1, 0 }
            },
            {
                    // pentomino representation L 8
                    { 1, 0 },
                    { 1, 0 },
                    { 1, 0 },
                    { 1, 1 }
            },
            {
                    // pentomino representation P 9
                    { 1, 1 },
                    { 1, 1 },
                    { 1, 0 },
            },
            {
                    // pentomino representation N 10
                    // { 1, 1, 0, 0 },
                    // { 0, 1, 1, 1 },
                    { 0, 0, 1, 1 },
                    { 1, 1, 1, 0 },
            },
            {
                    // Pentomino representation F 11
                    { 0, 1, 1 },
                    { 1, 1, 0 },
                    { 0, 1, 0 },
            }
    };

    public static int[][] getBasicDatabase(int num) {
        return basicDatabase[num];
    }

    // all pentominoes, inclusive their rotations
    public static ArrayList<int[][][]> database = new ArrayList<>();

    /**
     * Make the database, created based on the pentomino's pieces defined in
     * basicDatabase
     */
    public static void makeDatabase() {
        // do it for every piece of the basic database
        for (int i = 0; i < basicDatabase.length; i++) {
            // make a piece with maximal number of mutations an space
            int[][][] tempDatabase = new int[8][5][5];

            // take a piece of basic database, make it bigger so it fits in the 5*5, rotate
            // it j times, move it to the left upper corner so duplicates will be the same
            for (int j = 0; j < 4; j++) {
                tempDatabase[j] = moveToAbove(rotate(makeBigger(basicDatabase[i], 5), j));
            }

            // same as above, but flipping it ** do we need this? since we are not using
            // flips
            for (int j = 0; j < 4; j++) {
                tempDatabase[4 + j] = moveToAbove(rotate((makeBigger(basicDatabase[i], 5)), j));
            }

            // erase duplicates
            tempDatabase = eraseDuplicates(tempDatabase);

            // erase empty spaces in every piece
            for (int j = 0; j < tempDatabase.length; j++) {
                tempDatabase[j] = eraseEmptySpace(tempDatabase[j]);
            }

            // add the found pieces of just one basic piece to the database
            database.add(tempDatabase);
        }
    }

    /**
     * Rotate the matrix x times over 90 degrees
     * Assume that the matrix is a square!
     * It does not make a copy, so the return matrix does not have to be used
     * 
     * @param data:     a matrix
     * @param rotation: amount of rotation
     * @return the rotated matrix
     */
    public static int[][] rotate(int[][] data, int rotation) {
        int[][] tempData1 = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                tempData1[i][j] = data[i][j];
            }
        }

        // do it for the amount of times it needs to be rotated
        for (int k = 0; k < rotation; k++) {
            // make a matrix of the same size
            int[][] tempData2 = new int[tempData1.length][tempData1[0].length];
            // rotate it once and put it in tempData
            for (int i = 0; i < tempData1.length; i++) {
                for (int j = 0; j < tempData1[i].length; j++) {
                    tempData2[i][j] = tempData1[j][tempData1.length - i - 1];
                }
            }
            // put it back in the starting matrix so you can do it again
            for (int i = 0; i < tempData1.length; i++) {
                for (int j = 0; j < tempData1[i].length; j++) {
                    tempData1[i][j] = tempData2[i][j];
                }
            }
        }
        return tempData1;
    }

    /**
     * Expands a smaller than size*size matrix to a size*size matrix
     * It makes a copy, the input matrix stays unchanged
     * Assume that the input is smaller than size!!
     * 
     * @param data: a matrix
     * @param size: the square size of the new matrix
     * @return the size*size matrix
     */
    public static int[][] makeBigger(int[][] data, int size) {
        // make a matrix of size*size
        int[][] returnData = new int[size][size];
        // copies the matrix in the new matrix
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                returnData[i][j] = data[i][j];
            }
        }
        return returnData;
    }

    /**
     * Move matrix to the left above corner
     * Does not make a copy!
     * 
     * @param data: a matrix
     * @return the modified matrix
     */
    public static int[][] moveToAbove(int[][] data) {
        // the amount of rows it needs to make empty after moving up
        int amountToCut = 0;
        // do it for the the amount of rows there are to be sure
        for (int i = 0; i < data[0].length; i++) {
            // check if the first row is empty
            int empty = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[j][0] == 1) {
                    empty = 1;
                }
            }
            // if empty move everything one up
            if (empty == 0) {
                for (int j = 0; j < data.length; j++) {
                    for (int k = 1; k < data[j].length; k++) {
                        data[j][k - 1] = data[j][k];
                    }
                }
                amountToCut++;
            }
        }
        // make the last amountToCut rows empty, because these are copies
        for (int j = 0; j < data.length; j++) {
            for (int k = data[j].length - amountToCut; k < data[j].length; k++) {
                data[j][k] = 0;
            }
        }

        // the amount of columns it needs to make empty after moving up
        amountToCut = 0;
        // do it for the the amount of columns there are to be sure
        for (int i = 0; i < data.length; i++) {
            // check if the first column is empty
            int empty = 0;
            for (int j = 0; j < data[0].length; j++) {
                if (data[0][j] == 1) {
                    empty = 1;
                }
            }
            // if empty move everything one to the left
            if (empty == 0) {
                for (int j = 0; j < data[0].length; j++) {
                    for (int k = 1; k < data.length; k++) {
                        data[k - 1][j] = data[k][j];
                    }
                }
                amountToCut++;
            }
        }
        // make the last amountToCut columns empty, because these are copies
        for (int j = data.length - amountToCut; j < data.length; j++) {
            for (int k = 0; k < data[j].length; k++) {
                data[j][k] = 0;
            }
        }
        return data;
    }

    /**
     * Erase duplicates in a array of matrices
     * The input matrix stays unchanged
     * 
     * @param data an array of matrices
     * @return the array of matrices without duplicates
     */
    public static int[][][] eraseDuplicates(int[][][] data) {
        // make a counter that counts how many unique matrices there are
        int counter = 0;
        // check all matrices of the input
        for (int i = 0; i < data.length; i++) {
            // make an adder and set it to 1, if you find a duplicate, set it to 0
            int adder = 1;
            // go from the start till the matrix that you are checking now
            for (int j = 0; j < i; j++) {
                // check if equal
                if (isEqual(data[i], data[j])) {
                    adder = 0;
                }
            }
            counter += adder;
        }
        // make an array of matrices with size counter
        int[][][] returnData = new int[counter][][];
        // a counter that keeps how many matrices you already added to the new array of
        // matrices
        counter = 0;
        // check all matrices of the input
        for (int i = 0; i < data.length; i++) {
            // go from the start till the matrix that you are checking now
            boolean alreadyExist = false;
            for (int j = 0; j < i; j++) {
                if (isEqual(data[i], data[j])) {
                    alreadyExist = true;
                }
            }
            // if it's not already added, add it to the array
            if (alreadyExist == false) {
                returnData[counter] = data[i];
                // add one to counter, so next time you know where to add something
                counter++;
            }
        }
        return returnData;
    }

    /**
     * Check if two matrices are equal
     * Assume they have the same size
     * 
     * @param data1: the first matrix
     * @param data2: the second matrix
     * @return true if equal, false otherwise
     */
    public static boolean isEqual(int[][] data1, int[][] data2) {
        for (int i = 0; i < data1.length; i++) {
            for (int j = 0; j < data1[i].length; j++) {
                // Checks individual elements of the two matrecies
                if (data1[i][j] != data2[i][j]) {
                    return false;
                }
            }
        }
        // return true otherwise
        return true;
    }

    /**
     * 
     * Erase rows and columns that contain only zeros
     * 
     * @param data a matrix
     * @return the shrinken matrix
     */
    public static int[][] eraseEmptySpace(int[][] data) {
        // stores the first row and column with only 0s
        int amountOfRows = data.length;
        int amountOfColumns = data.length;
        // check all rows
        for (int i = 0; i < data[0].length && amountOfRows == data.length; i++) {
            // check if row i is empty
            int columnIsEmpty = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[j][i] == 1) {
                    columnIsEmpty = 1;
                }
            }
            // if empty, store that row number
            if (columnIsEmpty == 0) {
                amountOfRows = i;
            }
        }
        // check all columns
        for (int i = 0; i < data.length && amountOfColumns == data.length; i++) {
            // check if columns i is empty
            int rowIsEmpty = 0;
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 1) {
                    rowIsEmpty = 1;
                }
            }
            // if empty, store that column number
            if (rowIsEmpty == 0) {
                amountOfColumns = i;
            }
        }
        // make a matrix of the calculated size
        int[][] returnData = new int[amountOfColumns][amountOfRows];
        // copy the input matrix to the new matrix
        for (int i = 0; i < amountOfColumns; i++) {
            for (int j = 0; j < amountOfRows; j++) {
                returnData[i][j] = data[i][j];
            }
        }
        return returnData;
    }

    /**
     * @param args
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        makeDatabase();

        PrintWriter writer = new PrintWriter("pentominosForTetris.csv", "UTF-8");

        for (int i = 0; i < database.size(); i++) {
            for (int j = 0; j < database.get(i).length; j++) {
                writer.print(i + "," + j + "," + database.get(i)[j].length + "," + database.get(i)[j][0].length);

                for (int k = 0; k < database.get(i)[j].length; k++) {
                    for (int l = 0; l < database.get(i)[j][k].length; l++) {
                        writer.print("," + database.get(i)[j][k][l]);
                    }
                }

                writer.println();
            }
        }
        writer.close();
    }

    public static int getConvertedNum(int num) {
        int converter = 0;
        switch (num) {
            case 0:
                converter = 12;
                break;
            case 1:
                converter = 13;
                break;
            case 2:
                converter = 14;
                break;
            case 3:
                converter = 15;
                break;
            case 4:
                converter = 16;
                break;
            case 5:
                converter = 17;
                break;
            case 6:
                converter = 18;
                break;
            case 7:
                converter = 19;
                break;
            case 8:
                converter = 20;
                break;
            case 9:
                converter = 21;
                break;
            case 10:
                converter = 22;
                break;
            case 11:
                converter = 23;
                break;
            case 12:
                converter = 24;
                break;
            default:
                converter = -1;
                break;
        }
        return converter;
    }

}
