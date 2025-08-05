# Phase 2: Tetris Game

## Overview

This phase implements a complete Tetris game using pentomino pieces instead of traditional tetrominoes. The game features multiple gameplay modes including human player control, AI bot implementations, and automated optimal sequence play. Built with JavaFX, it provides an engaging graphical interface with score tracking and customizable game settings.

## Features

### Game Modes

- **Human Player**: Interactive gameplay with keyboard controls
- **Bot**: AI-powered gameplay with optimal piece placement (no movement animation)
- **Bot with Movement**: AI gameplay with visible movement animations
- **Best Sequence**: Demonstrates optimal play sequence achieving maximum score (1200 points)
- **Game Over Screen**: Options to restart, return to menu, or exit

### Technical Features

- Real-time piece movement and rotation
- Line clearing mechanics
- Score tracking and high score recording
- Customizable falling speed
- Multi-threaded game loop for smooth performance
- CSV-based piece configuration system

## Project Structure

- `StartingPage.java` - Main menu interface and game mode selection
- `HumanGame.java` - Human player game implementation
- `Bot.java` - AI bot without movement animations
- `BotMovement.java` - AI bot with visible movement animations
- `BestSequence.java` - Optimal sequence demonstration
- `TetrisGameArea.java` - Core game logic and rendering
- `TetrisBlock.java` - Individual tetris piece implementation
- `GameOverPicture.java` - Game over screen interface
- `ScoreReader.java` - Score management and persistence
- `PentominoBuilder.java` - Pentomino piece generation utility
- `UI.java` - Shared UI components and utilities

## Prerequisites

1. **Java Development Environment**: Java 17 or higher
2. **JavaFX**: Properly configured JavaFX SDK
3. **CSV Generation**: Run `PentominoBuilder.java` once to generate required pentomino data

## How to Run

### Initial Setup
```bash
# 1. First, generate the required CSV file
javac PentominoBuilder.java
java PentominoBuilder

# 2. Run the main application
javac StartingPage.java
java StartingPage
```

### Alternative IDE Setup
1. Open the project in your preferred IDE
2. Run `PentominoBuilder.java` first to generate the CSV file
3. Run `StartingPage.java` to launch the game

## Controls

### Human Player Mode

| Key | Alternative | Action |
|-----|-------------|--------|
| `A` | `←` (Left Arrow) | Move piece left |
| `D` | `→` (Right Arrow) | Move piece right |
| `S` | `↓` (Down Arrow) | Move piece down |
| `W` | `↑` (Up Arrow) | Rotate piece |
| `Spacebar` | `Enter` | Drop piece (free fall) |

## Customization

### Adjusting Game Speed

To modify the falling speed of pieces:

1. Open `TetrisGameArea.java`
2. Locate the `repaint()` method
3. Modify the `Thread.sleep(300)` value:
   - Lower values = faster gameplay
   - Higher values = slower gameplay

```java
// Example: Make the game faster
Thread.sleep(200);  // Faster than default 300ms

// Example: Make the game slower  
Thread.sleep(500);  // Slower than default 300ms
```

## Game Modes Explained

### Human Player
Interactive mode where players control piece movement and rotation using keyboard inputs. Features real-time response and traditional Tetris mechanics.

### Bot (No Animation)
Automated gameplay where the AI calculates optimal placement and instantly positions pieces. This mode typically achieves higher scores due to perfect placement without movement delays.

### Bot with Movement
Similar to the standard bot but includes visual movement animations, showing how the AI navigates pieces to their optimal positions.

### Best Sequence
Demonstrates a pre-calculated optimal sequence that achieves the theoretical maximum score of 1200 points using all 12 pentomino pieces.

## Technical Implementation

- **Multi-threading**: Separate threads for game logic and UI rendering
- **Event-driven architecture**: Keyboard input handling and game state management
- **Object-oriented design**: Modular code structure with clear separation of concerns
- **File I/O**: CSV-based configuration and score persistence
