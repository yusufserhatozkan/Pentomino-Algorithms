# Phase 3: 3D Knapsack Visualizer

## Overview

This phase implements a sophisticated 3D knapsack problem solver with real-time visualization. The application efficiently packs pentomino pieces into a cargo space while providing an interactive 3D visualization of the packing process. Built with JavaFX 3D graphics, it demonstrates advanced spatial algorithms and geometric optimization techniques.

## Features

### Core Functionality
- **3D Cargo Space**: Realistic cargo container dimensions (16.5m × 2.5m × 4.0m)
- **Pentomino Packing**: Optimal placement of 3D pentomino pieces
- **Real-time Visualization**: Dynamic 3D rendering of the packing process
- **Interactive Camera**: 360-degree viewing with zoom and rotation controls
- **Optimization Algorithms**: Multiple recursive approaches for optimal space utilization

### Technical Features
- **3D Graphics**: Advanced JavaFX 3D scene rendering
- **Spatial Algorithms**: Recursive backtracking for optimal packing
- **Performance Optimization**: Efficient space utilization calculations
- **Interactive Controls**: Mouse-based camera navigation
- **Animation System**: Smooth piece placement animations

## Project Structure

- `StartingPage.java` - Main application entry point and launcher
- `CargoSpace.java` - Core 3D cargo space logic and data structures
- `CargoSpaceVisualizer.java` - 3D visualization engine and JavaFX interface
- `Pentomino.java` - 3D pentomino piece definitions and transformations
- `RecursionLau.java` - Primary recursive packing algorithm implementation
- `RecursionLau2.java` - Alternative recursive approach for optimization

## Prerequisites

1. **Java Development Environment**: Java 17 or higher
2. **JavaFX 3D Support**: JavaFX SDK with 3D graphics capabilities
3. **Hardware Requirements**: Graphics card with 3D acceleration support

## How to Run

### Command Line
```bash
# Compile the project
javac -cp "path/to/javafx/lib/*" Phase3/*.java

# Run the application
java -cp ".:path/to/javafx/lib/*" --module-path path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics Phase3.StartingPage
```

### IDE Setup
1. Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Configure JavaFX SDK in your project settings
3. Add JavaFX modules to VM options: `--add-modules javafx.controls,javafx.fxml,javafx.graphics`
4. Run `StartingPage.java`

## Cargo Space Specifications

### Physical Dimensions
- **Length**: 16.5 meters (33 units @ 0.5m per unit)
- **Width**: 2.5 meters (5 units @ 0.5m per unit)  
- **Height**: 4.0 meters (8 units @ 0.5m per unit)
- **Total Volume**: 165 cubic meters

### Unit System
- Each unit represents 0.5 meters in real-world scale
- Grid-based placement system for precise positioning
- Collision detection prevents overlapping pieces

## 3D Visualization Controls

### Camera Navigation
- **Mouse Drag**: Rotate camera around the cargo space
- **Mouse Wheel**: Zoom in/out for detailed or overview perspective
- **Right-Click Drag**: Pan camera position
- **Reset View**: Return to default viewing angle

### Visualization Features
- **Color-coded Pieces**: Each pentomino type has distinct coloring
- **Wireframe Mode**: Toggle between solid and wireframe rendering
- **Transparency**: Adjustable opacity for better internal visibility
- **Animation Speed**: Configurable placement animation timing

## Algorithm Details

### Recursive Backtracking
The application employs sophisticated recursive algorithms to:

1. **Analyze Available Space**: Identify potential placement positions
2. **Test Orientations**: Try all possible rotations and orientations
3. **Validate Placement**: Ensure no collisions or boundary violations
4. **Optimize Packing**: Maximize space utilization efficiency
5. **Backtrack**: Undo placements that lead to suboptimal solutions

### Performance Optimizations
- **Pruning**: Early termination of non-viable solution paths
- **Heuristics**: Intelligent piece ordering for faster convergence
- **Memory Management**: Efficient 3D array operations
- **Parallel Processing**: Multi-threaded computation where applicable

## Customization Options

### Modifying Cargo Dimensions
Edit the constants in `CargoSpace.java`:
```java
public static final int DEPTH = 33;    // Length units
private static final int WIDTH = 5;     // Width units  
private static final int HEIGHT = 8;    // Height units
```

### Adjusting Visualization
Modify rendering parameters in `CargoSpaceVisualizer.java`:
- Camera positioning and movement speed
- Animation timing and effects
- Color schemes and materials
- Lighting and shadow settings

## Technical Implementation

### 3D Graphics Pipeline
- **Scene Graph**: Hierarchical 3D object organization
- **Materials System**: Phong shading with customizable properties  
- **Lighting Model**: Ambient and directional lighting setup
- **Coordinate System**: Right-handed 3D coordinate space

### Data Structures
- **3D Arrays**: Efficient spatial representation
- **Transformation Matrices**: Rotation and translation calculations
- **Collision Detection**: Boundary checking and overlap prevention
- **State Management**: Undo/redo functionality for algorithm steps

## Applications

This project demonstrates practical applications in:
- **Logistics Optimization**: Cargo container packing
- **Warehouse Management**: Space utilization planning
- **3D Puzzle Solving**: Geometric constraint satisfaction
- **Algorithm Visualization**: Educational tool for spatial algorithms
