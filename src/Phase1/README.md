# Phase 1: Pentomino Solver

## Overview

This phase implements a pentomino tiling solver that fills a user-defined rectangular area with selected pentomino pieces. The application uses recursive backtracking algorithms to find valid solutions and provides a graphical visualization of the results.

## What are Pentominoes?

Pentominoes are geometric shapes composed of 5 connected unit squares. There are 12 unique pentomino pieces, each with a distinct shape. The challenge is to arrange selected pieces to completely fill a rectangular area without gaps or overlaps.

## Features

- **Interactive Input**: Users can specify rectangle dimensions and select which pentomino pieces to use
- **Multiple Search Algorithms**: Implements both brute force and recursive backtracking approaches
- **Visual Output**: Displays the solution in a colorful graphical interface using JavaFX
- **Input Validation**: Comprehensive error checking for invalid inputs
- **Animation Support**: Optional step-by-step visualization of the solving process

## Project Structure

- `PentominoBuilder.java` - Core logic for pentomino piece construction and manipulation
- `PentominoDatabase.java` - Database containing all pentomino piece definitions
- `SearchBruteForce.java` - Brute force search algorithm implementation
- `SearchRecrusion.java` - Recursive backtracking search algorithm
- `UI.java` - JavaFX user interface for visualization
- `pentominos.csv` - Data file containing pentomino piece configurations

## How to Run

1. **Prerequisites**: Ensure JavaFX is properly configured in your development environment
2. **Execution**: Run either `SearchBruteForce.java` or `SearchRecrusion.java`
3. **Input Phase**:
   - Enter rectangle dimensions (width and height)
   - Select pentomino pieces to use (input validation will guide you)
   - Continue until enough pieces are selected to fill the rectangle
4. **Results**: View the solution in the graphical window or see "No solution found" message in the terminal
5. **Exit**: Press Enter to close the application

## Usage Tips

- **Animation**: To enable/disable the step-by-step solving animation, modify line 163 in the search files (uncomment `ui.setState(field);`)
- **Performance**: Animation significantly slows down the solving process
- **Invalid Inputs**: The system will prompt for corrections if invalid rectangle dimensions or pentomino selections are entered

## Algorithm Details

The solver uses recursive backtracking to systematically try placing each selected pentomino piece in all possible positions and orientations until a complete solution is found or all possibilities are exhausted.

