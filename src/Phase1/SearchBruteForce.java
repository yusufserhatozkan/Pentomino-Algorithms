package Phase1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/* !!!!!
 * To DISABLE animation, go to line 163 and comment the code: "ui.setState(field);"
 */

/**
 * This class includes the methods to support the search of a solution.
 */
public class SearchBruteForce {
	static int[] dimensions = askUserPentominoDimensions(); // calls the method to get dimensions from user
	public static final int horizontalGridSize = dimensions[1]; // assigns the row the user entered
	public static final int verticalGridSize = dimensions[0];// assigns the column the user entered

	public static char[] input = { 'W', 'I', 'T', 'Z', 'L' }; // replace with user inputted pentominos

	// Static UI class to display the board
	public static UI ui = new UI(horizontalGridSize, verticalGridSize, 60); // displays the UI

	/**
	 * Helper function which starts a basic search algorithm
	 */
	public static void search() {
		// initialize an empty board (of -1's) using users dimension input
		int[][] field = new int[horizontalGridSize][verticalGridSize];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				// -1 in the state matrix corresponds to empty square
				// Any positive number identifies the ID of the pentomino
				field[i][j] = -1;
			}
		}
		// start the basic search
		basicSearch(field);
	}

	/**
	 * Get as input the character representation of a pentomino and translate it
	 * into its corresponding numerical value (ID)
	 * 
	 * @param character a character representating a pentomino
	 * @return the corresponding ID (numerical value)
	 */
	private static int characterToID(char character) {
		int pentID = -1;
		if (character == 'X') {
			pentID = 0;
		} else if (character == 'I') {
			pentID = 1;
		} else if (character == 'Z') {
			pentID = 2;
		} else if (character == 'T') {
			pentID = 3;
		} else if (character == 'U') {
			pentID = 4;
		} else if (character == 'V') {
			pentID = 5;
		} else if (character == 'W') {
			pentID = 6;
		} else if (character == 'Y') {
			pentID = 7;
		} else if (character == 'L') {
			pentID = 8;
		} else if (character == 'P') {
			pentID = 9;
		} else if (character == 'N') {
			pentID = 10;
		} else if (character == 'F') {
			pentID = 11;
		}
		return pentID;
	}

	/**
	 * Basic implementation of a search algorithm. It is not a bruto force
	 * algorithms but randomly takes possible combinations and positions to find a
	 * possible solution.
	 * 
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
	private static void basicSearch(int[][] field) {
		Scanner scan = new Scanner(System.in);

		Random random = new Random();
		boolean solutionFound = false;

		while (!solutionFound) {
			solutionFound = true;

			// Empty board again to find a solution
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					field[i][j] = -1;
				}
			}

			// Put all pentominoes with random rotation/flipping on a random position on the
			// board
			for (int i = 0; i < input.length; i++) {

				// Choose a pentomino and randomly rotate/flip it
				int pentID = characterToID(input[i]);
				int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
				int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

				// Randomly generate a position to put the pentomino on the board
				int x;
				int y;
				if (horizontalGridSize < pieceToPlace.length) {
					// this particular rotation of the piece is too long for the field
					x = -1;
				} else if (horizontalGridSize == pieceToPlace.length) {
					// this particular rotation of the piece fits perfectly into the width of the
					// field
					x = 0;
				} else {
					// there are multiple possibilities where to place the piece without leaving the
					// field
					x = random.nextInt(horizontalGridSize - pieceToPlace.length + 1);
				}

				if (verticalGridSize < pieceToPlace[0].length) {
					// this particular rotation of the piece is too high for the field
					y = -1;
				} else if (verticalGridSize == pieceToPlace[0].length) {
					// this particular rotation of the piece fits perfectly into the height of the
					// field
					y = 0;
				} else {
					// there are multiple possibilities where to place the piece without leaving the
					// field
					y = random.nextInt(verticalGridSize - pieceToPlace[0].length + 1);
				}

				// If there is a possibility to place the piece on the field, addPiece on field
				if (x >= 0 && y >= 0) {
					if (checkPossiblePlace(field, pieceToPlace, x, y) && checkIsolation(field, x, y)) {
						addPiece(field, pieceToPlace, pentID, x, y);
					}
				}
			}

			// Check whether complete field is filled (if -1's still in the field or not)
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (field[i][j] == -1) {
						solutionFound = false;
					}
				}
			}
			try {
				Thread.sleep(0); // see the animation of pentominos slower
				ui.setState(field); // comment to not see the animation
			} catch (InterruptedException intrExc) {
				break;
			}

			// in the case a solution is found
			if (solutionFound) {
				// display the field
				ui.setState(field);
				System.out.println("Solution Found");
				System.out.println(Arrays.toString(input) + " is a valid solution");
			}
		}
	}

	/**
	 * Adds a pentomino to the position on the field (overriding current board at
	 * that position)
	 * 
	 * @param field   a matrix representing the board to be fulfilled with
	 *                pentominoes
	 * @param piece   a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x       x position of the pentomino
	 * @param y       y position of the pentomino
	 */
	public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
		for (int i = 0; i < piece.length; i++) // loop over x position of pentomino
		{
			for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
			{
				if (piece[i][j] == 1) {
					// add the ID of the pentomino to the board if the pentomino occupies this
					// square
					field[x + i][y + j] = pieceID;
				}
			}
		}
	}

	/**
	 * Checks if it is possible to place a pentomino at a position in the field is
	 * possible without overlapping and going out of bounds.
	 * 
	 * @param field          a matrix representing the board to be fulfilled with
	 *                       pentominoes
	 * @param pentominoPiece a matrix representing the pentomino to be placed in the
	 *                       board
	 * @param x              x position of the field
	 * @param y              y position of the field
	 * @return no overlap and within bounds, so it can place the piece
	 * 
	 */
	public static boolean checkPossiblePlace(int[][] field, int[][] pentominoPiece, int x, int y) {
		for (int i = 0; i < pentominoPiece.length; i++) { // row of pentominoPiece
			for (int k = 0; k < pentominoPiece[i].length; k++) { // column of pentominoPiece
				// check if it is within the bounds of the field
				// if (!checkIsolation(field, i, k)) {
				// 	return false;
				// }
				if (x + i >= 0 && x + i < field.length && y + k >= 0 && y + k < field[0].length) {
					// check for overlapping
					if (pentominoPiece[i][k] == 1 && field[x + i][y + k] != -1) {
						return false; // overlapping, so it cant place the piece at this position
					}
				} else {
					return false; // out of bounds, so it cant place the piece at this position

				} // ui.setState(field); //uncomment to have animation
			}
		}
		return true; // no overlap and within bounds, so it can place the piece
	}

	/**
	 * checks if the matrix has empty spaces surrounded by 1's, which would be
	 * invalid
	 * 
	 * @param field a matrix representing the board to be fulfilled with
	 *              pentominoes
	 * @param i
	 * @param j
	 * @return if 0's are isolated or not
	 */
	public static boolean checkIsolation(int[][] field, int i, int j) {
		int x = i;
		int y = j;
		int[][] arr = field;

		// creating a new array with borders of 1's

		// calculate new dimensions
		int newRows = arr.length + 2;
		int newCols = arr[0].length + 2;

		// create new array with added size
		int[][] newArrWithZeros = new int[newRows][newCols]; // 1's around

		// fill the new array with 1's on the borders and copy the original array
		// content into the center
		for (int l = 0; l < newRows; l++) {
			for (int m = 0; m < newCols; m++) {
				if (l == 0 || m == 0 || l == newRows - 1 || m == newCols - 1) {
					newArrWithZeros[l][m] = 1; // place 1's on the outside
				} else {
					newArrWithZeros[l][m] = arr[l - 1][m - 1]; // otherwise fill new array with old array numbers
				}
			}
		}

		// replaces all -1's with 0's
		int[][] newArr = new int[newArrWithZeros.length][newArrWithZeros[0].length];
		for (int a = 0; a < newArrWithZeros.length; a++) {
			for (int b = 0; b < newArrWithZeros[a].length; b++) {
				if (newArrWithZeros[a][b] == -1) { // if the array has a -1
					newArr[a][b] = 0; // replace it with a 0
				} else {
					newArr[a][b] = newArrWithZeros[a][b]; // otherwise fill new array with old numbers
				}
			}
		}

		// all different cases of isolations that can be scrapped
		// if one 0 is surrounded with 1's
		// if two 0's are surrounded with 1's
		// if three 0's are surrounded with 1's
		// if four 0's are surrounded with 1's
		if (newArr[x][y] != 1) { // if its a 0
			switch (newArr[x][y]) {

				// 1 zeros
				case 1:
					if (newArr[x + 1][y] == 1 && newArr[x][y + 1] == 1) {
						return false;
					}
					break;

				// 2 horizonatal zeros
				case 2:
					if (newArr[x + 1][y] == 0 && newArr[x + 1][y + 1] == 1
							&& newArr[x + 2][y] == 1
							&& newArr[x][y + 1] == 1) {
						return false;
					}
					break;
				// 3 horizonatal zeros
				case 3:
					if (newArr[x + 1][y] == 0 && newArr[x][y + 1] == 1 && newArr[x + 1][y + 1] == 1
							&& newArr[x + 2][y] == 0 && newArr[x + 2][y + 1] == 1 && newArr[x + 3][y] == 1) {
						return false;
					}
					break;
				// 4 horizonatal zeros
				case 4:
					if (newArr[x + 1][y] == 0 && newArr[x + 1][y + 1] == 1 && newArr[x + 2][y] == 0
							&& newArr[x + 2][y + 1] == 1 && newArr[x + 3][y] == 0
							&& newArr[x + 3][y + 1] == 1 && newArr[x + 4][y] == 1 && newArr[x][y + 1] == 1) {
						return false;
					}
					break;

				// 2 vertical zeros
				case 5:
					if (newArr[x][y + 1] == 0 && newArr[x - 1][y + 1] == 1 && newArr[x + 1][y + 1] == 1
							&& newArr[x][y + 2] == 1 && newArr[x + 1][y] == 1) {
						return false;
					}
					break;
				// 3 vertical zeros
				case 6:
					if (newArr[x][y + 1] == 0 && newArr[x - 1][y + 1] == 1 && newArr[x + 1][y + 1] == 1
							&& newArr[x][y + 2] == 0 && newArr[x + 1][y] == 1
							&& newArr[x - 1][y + 2] == 1 && newArr[x + 1][y + 2] == 1 && newArr[x][y + 3] == 1) {
						return false;
					}
					break;
				// 4 vertical zeros
				case 7:
					if (newArr[x][y + 1] == 0 && newArr[x - 1][y + 1] == 1 && newArr[x + 1][y + 1] == 1
							&& newArr[x][y + 2] == 0 && newArr[x + 1][y] == 1
							&& newArr[x - 1][y + 2] == 1 && newArr[x + 1][y + 2] == 1 && newArr[x][y + 3] == 0
							&& newArr[x - 1][y + 3] == 1
							&& newArr[x + 1][y + 3] == 1 && newArr[x][y + 4] == 1) {
						return false;
					}
					break;

				// 3 zeros L shape

				// case 1
				case 8:
					if (newArr[x + 1][y] == 0
							&& newArr[x][y - 1] == 0 && newArr[x + 1][y + 1] == 1 && newArr[x + 2][y] == 1
							&& newArr[x][y + 2] == 1 && newArr[x - 1][y + 1] == 1) {
						return false;
					}
					break;
				// case 2
				case 9:
					if (newArr[x + 1][y] == 0 && newArr[x + 1][y + 1] == 0
							&& newArr[x][y + 1] == 1 && newArr[x + 2][y + 1] == 1 && newArr[x + 2][y] == 1
							&& newArr[x + 1][y + 2] == 1) {
						return false;
					}
					break;
				// case 3
				case 10:
					if (newArr[x + 1][y] == 1 && newArr[x][y + 1] == 0 && newArr[x + 1][y + 1] == 1
							&& newArr[x][y + 2] == 1
							&& newArr[x - 1][y + 2] == 1 && newArr[x - 1][y + 1] == 0 && newArr[x - 2][y + 1] == 1) {
						return false;
					}
					break;
				// case 4
				case 11:
					if (newArr[x + 1][y] == 1 && newArr[x][y + 1] == 0 && newArr[x + 1][y + 1] == 0
							&& newArr[x + 2][y + 1] == 1
							&& newArr[x][y + 2] == 1 && newArr[x - 1][y + 1] == 1 && newArr[x + 1][y + 2] == 1) {
						return false;
					}
					break;
				// 4 zeros
				// case 1
				case 12:
					if (newArr[x][y + 1] == 0 && newArr[x + 1][y + 1] == 0 && newArr[x + 2][y + 1] == 0
							&& newArr[x + 1][y] == 1 && newArr[x + 2][y] == 1 && newArr[x + 3][y + 1] == 1
							&& newArr[x + 2][y + 2] == 0
							&& newArr[x + 1][y + 2] == 1 && newArr[x][y + 2] == 1 && newArr[x - 1][y + 1] == 1
							&& newArr[x + 2][y + 2] == 1) {
						return false;
					}
					break;
				// case 2
				case 13:
					if (newArr[x - 1][y + 1] == 0 && newArr[x][y + 1] == 0 && newArr[x + 1][y + 1] == 0
							&& newArr[x + 1][y] == 1 && newArr[x + 2][y + 1] == 1 && newArr[x + 1][y + 2] == 1
							&& newArr[x][y + 2] == 1
							&& newArr[x - 1][y + 2] == 1 && newArr[x - 2][y + 1] == 1) {
						return false;
					}
					break;
				// case 3
				case 14:
					if (newArr[x][y + 1] == 0 && newArr[x - 1][y + 1] == 0 && newArr[x - 2][y + 1] == 0 &&
							newArr[x + 1][y] == 1 && newArr[x + 1][y + 1] == 1 && newArr[x][y + 2] == 1
							&& newArr[x - 1][y + 2] == 1
							&& newArr[x - 2][y + 2] == 1 && newArr[x - 3][y + 1] == 1) {
						return false;
					}
					break;
				// case 4
				case 15:
					if ((newArr[x + 1][y] == 0 && newArr[x + 2][y] == 0 && newArr[x + 2][y + 1] == 0
							&& newArr[x + 3][y] == 1 && newArr[x + 3][y + 1] == 1 && newArr[x + 2][y + 2] == 1
							&& newArr[x + 1][y + 1] == 1
							&& newArr[x][y + 1] == 1)) {
						return false;
					}
					break;
				// case 5
				case 16:
					if ((newArr[x + 1][y] == 0 && newArr[x + 2][y] == 0 && newArr[x + 1][y + 1] == 0
							&& newArr[x + 3][y] == 1 && newArr[x + 1][y + 2] == 1 && newArr[x + 2][y + 1] == 1
							&& newArr[x][y + 1] == 1)) {
						return false;
					}
					break;

				// case 6
				case 17:
					if (newArr[x + 1][y] == 0 && newArr[x + 2][y] == 0 && newArr[x][y + 1] == 0
							&& newArr[x + 3][y] == 1 && newArr[x][y + 2] == 1 && newArr[x + 2][y + 1] == 1
							&& newArr[x + 1][y + 1] == 1 && newArr[x - 1][y + 1] == 1) {
						return false;
					}
					break;

				// case 7
				case 18:
					if (newArr[x][y + 1] == 0 && newArr[x][y + 2] == 0 && newArr[x + 1][y] == 0
							&& newArr[x - 1][y + 1] == 1 && newArr[x - 1][y + 2] == 1 && newArr[x][y + 3] == 1
							&& newArr[x + 2][y] == 1 && newArr[x + 1][y + 1] == 1 && newArr[x + 1][y + 2] == 1) {
						return false;
					}
					break;
				// case 8
				case 19:
					if (newArr[x][y + 1] == 0 && newArr[x][y + 2] == 0 && newArr[x + 1][y + 1] == 0
							&& newArr[x - 1][y + 1] == 1 && newArr[x - 1][y + 2] == 1 && newArr[x][y + 3] == 1
							&& newArr[x + 1][y] == 1 && newArr[x + 2][y + 1] == 1 && newArr[x + 1][y + 2] == 1) {
						return false;
					}
					break;
				// case 9
				case 20:
					if (newArr[x][y + 1] == 0 && newArr[x][y + 2] == 0 && newArr[x + 1][y + 2] == 0
							&& newArr[x - 1][y + 1] == 1 && newArr[x - 1][y + 2] == 1 && newArr[x][y + 3] == 1
							&& newArr[x + 1][y] == 1 && newArr[x + 1][y + 1] == 1 && newArr[x + 2][y + 2] == 1
							&& newArr[x + 1][y + 3] == 1) {
						return false;
					}
					break;
				// case 10
				case 21:
					if (newArr[x + 1][y] == 0 && newArr[x + 1][y + 1] == 0 && newArr[x + 1][y + 2] == 0
							&& newArr[x + 2][y] == 1 && newArr[x + 2][y + 1] == 1 && newArr[x + 2][y + 2] == 1
							&& newArr[x + 1][y + 3] == 1 && newArr[x][y + 2] == 1 && newArr[x][y + 1] == 1) {
						return false;
					}
					break;
				// case 11
				case 22:
					if (newArr[x][y + 1] == 0 && newArr[x][y + 2] == 0 && newArr[x - 1][y + 1] == 0
							&& newArr[x + 1][y] == 1 && newArr[x + 1][y + 1] == 1 && newArr[x + 1][y + 2] == 1
							&& newArr[x][y + 3] == 1 && newArr[x - 1][y + 2] == 1 && newArr[x - 2][y + 1] == 1)

					{
						return false;
					}
					break;
				// case 12
				case 23:
					if (newArr[x][y + 1] == 0 && newArr[x][y + 2] == 0 && newArr[x - 1][y + 2] == 0
							&& newArr[x + 1][y] == 1 && newArr[x + 1][y + 1] == 1 && newArr[x + 1][y + 2] == 1
							&& newArr[x][y + 3] == 1 && newArr[x - 2][y + 2] == 1 && newArr[x - 1][y + 3] == 1
							&& newArr[x - 1][y + 1] == 1)

					{
						return false;
					}
					break;
				// case 13
				case 24:
					if (newArr[x + 1][y] == 0 && newArr[x][y + 1] == 0 && newArr[x + 1][y + 1] == 0
							&& newArr[x + 2][y] == 1 && newArr[x + 2][y + 1] == 1 && newArr[x + 1][y + 2] == 1
							&& newArr[x][y + 2] == 1 && newArr[x - 1][y + 1] == 1)

					{
						return false;
					}
					break;

				default:
					return true;
			}
		}
		return true;

	}

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
	public static void main(String[] args) {
		String[] pentominoStrings = askUserPentominoArray();

		input = new char[pentominoStrings.length];
		for (int i = 0; i < pentominoStrings.length; i++) {
			input[i] = pentominoStrings[i].charAt(0);
		}

		System.out.println(input);

		// start timer
		long startTime = System.currentTimeMillis(); // Record the start time

		search(); // search method

		// end timer
		long endTime = System.currentTimeMillis(); // Record the end time
		long elapsedTime = endTime - startTime; // Calculate elapsed time in milliseconds
		System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");

	}

	/**
	 * 
	 * @askUserPentominioDimensions asks the user for the row and column size
	 * @askUserPentominioArray asks the user for pentominos and puts them in an
	 *                         array if the dimensions are correct
	 * @return the array of [row, column]
	 */
	public static int[] askUserPentominoDimensions() {
		int[] dimensions = { 1, 1 }; // (row, column)
		Scanner scan = new Scanner(System.in);

		System.out.println("What do you want row size to be?");
		dimensions[0] = scan.nextInt(); // index 0 will be row
		System.out.println("What do you want column size to be?");
		dimensions[1] = scan.nextInt(); // index 1 will be column

		// all the different ways it is not possible for rows and columns to be together
		while (dimensions[0] * dimensions[1] % 5 != 0 || dimensions[0] <= 0 || dimensions[1] <= 0
				|| (dimensions[0] * dimensions[1] == 10) || dimensions[0] > 12 || dimensions[1] > 12
				|| dimensions[0] * dimensions[1] > 60 || dimensions[0] == 2 || dimensions[1] == 2) {
			System.out.println("Invalid input. Try again.");

			System.out.println("What do you want row size to be?");
			dimensions[0] = scan.nextInt(); // index 0 will be row

			System.out.println("What do you want column size to be?");
			dimensions[1] = scan.nextInt(); // index 1 will be column
		}
		return dimensions;
	}

	/**
	 * asks user for the set of pentamino using the user inputted dimensions
	 * 
	 * @return the array of pentominos
	 */
	public static String[] askUserPentominoArray() {
		Scanner scan = new Scanner(System.in);
		int row = verticalGridSize; // row value
		int column = horizontalGridSize; // column value
		System.out.println("Row: " + row + " Column: " + column);

		int size = 0; // size of the input of user (pentomino user input amount)

		// decides how many inputs user can enter
		if (row % 5 == 0) {
			size = column;
		} else if (column % 5 == 0) {
			size = row;
		} else {
			size = 0; // size will be 0 if not multiple of 5
		}

		// Arraylist of all the possible pentominos
		ArrayList<String> pentominoOptions = new ArrayList<>(12);
		pentominoOptions.add("X");
		pentominoOptions.add("I");
		pentominoOptions.add("Z");
		pentominoOptions.add("T");
		pentominoOptions.add("U");
		pentominoOptions.add("V");
		pentominoOptions.add("W");
		pentominoOptions.add("Y");
		pentominoOptions.add("L");
		pentominoOptions.add("P");
		pentominoOptions.add("N");
		pentominoOptions.add("F");

		String[] falseDimensions = { "invalid input of dimenstions" }; // return this if false dimensions
		String[] pentominoShape = new String[size]; // array for the different pentomino shapes

		if (size == 0 || size == 2 || size > 12) { // invalid dimensions
			System.out.println("NO VALID INPUT OF DIMENSIONS");
			return falseDimensions;
		} else if (size == 1) { // if one of the dimension is 1, show UI with I
			for (int i = 0; i < size; i++) {
				String pentominoUserInput = "I";
				pentominoShape[i] = pentominoUserInput;
			}
			return pentominoShape;
		} else if (size == 12) { // if size is 12, then user doesnt enter any input
			// define a static array of pentomino shapes for size 12
			String[] predefinedPentominos = { "I", "L", "U", "Y", "F", "X", "N", "T", "Z", "W", "V", "P" };
			System.out.println("All pentominos have been used: " + predefinedPentominos[0] + ", "
					+ predefinedPentominos[1] + ", "
					+ predefinedPentominos[2] + ", " + predefinedPentominos[3] + ", " + predefinedPentominos[4] + ", "
					+ predefinedPentominos[5] + ", " + predefinedPentominos[6] + ", " + predefinedPentominos[7] + ", "
					+ predefinedPentominos[8] + ", " + predefinedPentominos[9] + ", " + predefinedPentominos[10] + ", "
					+ predefinedPentominos[11] + "!");
			return predefinedPentominos;
		} else { // for all other cases
			System.out.println("In order to exit the game type: exit.");
			for (int i = 0; i < size; i++) {
				System.out.println("Pentomino options: " + pentominoOptions); // prints out the options the user has
				System.out.print("Give me pentomino " + (i + 1) + "/" + size + ": ");
				String pentominoUserInput = scan.nextLine().toUpperCase(); // asks user for input

				if (pentominoUserInput.equals("exit".toUpperCase())) { // check if user inputted exit
					System.exit(0); // exit the UI
				}

				// checks if what user enterd is valid
				if (pentominoOptions.contains(pentominoUserInput)) { // if valid, then add input to array
					pentominoShape[i] = pentominoUserInput; // adds the input to the array
					pentominoOptions.remove(pentominoUserInput); // removes the input from the arraylist
				} else { // otherwise make them repeat an input until valid input is entered
					while (!pentominoOptions.contains(pentominoUserInput)) {
						System.out.println("Please enter the correct pentomino option");
						System.out.println("Pentomino options: " + pentominoOptions); // prints out the options the user
																						// has
						System.out.print("Give me pentomino " + (i + 1) + ": ");
						pentominoUserInput = scan.nextLine().toUpperCase(); // asks user for input
					}
					pentominoShape[i] = pentominoUserInput; // adds the input to the array
					pentominoOptions.remove(pentominoUserInput); // removes the input from the arraylist
				}
			}
			return pentominoShape;
		}
	}
}