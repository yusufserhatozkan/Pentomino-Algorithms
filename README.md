# Java Algorithms Project: Pentomino Applications

## Overview

This repository contains a comprehensive university group project developed during the 2023 academic year, showcasing advanced algorithmic problem-solving through three distinct phases. Each phase demonstrates different computational challenges: geometric tiling optimization, game AI implementation, and 3D spatial optimization with visualization.

The project explores the versatile applications of pentomino pieces (5-unit geometric shapes) across multiple domains, from puzzle solving to game development and logistics optimization.

---

## 🎯 Project Phases

### Phase 1: Pentomino Solver
**Geometric Tiling Optimization**

A sophisticated recursive search algorithm that solves pentomino tiling puzzles by filling user-defined rectangular areas with selected pentomino pieces.

**Key Features:**
- Interactive rectangle dimension input
- Multiple search algorithms (brute force & recursive backtracking)
- Real-time graphical visualization with JavaFX
- Step-by-step animation support
- Comprehensive input validation

**Technical Highlights:**
- Recursive backtracking algorithms
- Geometric constraint satisfaction
- Interactive JavaFX interface

### Phase 2: Tetris Game
**AI-Powered Game Implementation**

A complete Tetris game using pentomino pieces with multiple gameplay modes, including advanced AI bot implementations and optimal sequence demonstrations.

**Key Features:**
- **Human Player Mode**: Full keyboard control with dual input schemes
- **AI Bot Mode**: Optimal piece placement without animations
- **Bot with Movement**: AI with visible movement animations
- **Best Sequence Mode**: Demonstrates theoretical maximum score (1200 points)
- Score tracking and game state management

**Technical Highlights:**
- Multi-threaded game engine
- AI decision-making algorithms
- Event-driven architecture
- Real-time performance optimization

### Phase 3: 3D Knapsack Visualizer
**Spatial Optimization with 3D Graphics**

An advanced 3D knapsack problem solver with real-time visualization, optimally packing pentomino pieces into cargo spaces with interactive 3D rendering.

**Key Features:**
- Realistic cargo container simulation (16.5m × 2.5m × 4.0m)
- Interactive 3D visualization with camera controls
- Multiple recursive optimization algorithms
- Real-time packing process animation
- Advanced collision detection

**Technical Highlights:**
- JavaFX 3D graphics pipeline
- Spatial algorithms and geometric transformations
- Interactive camera navigation
- Performance-optimized 3D rendering

---

## 🛠️ Technologies Used

- **Java 17+**: Core programming language
- **JavaFX 21.0.1**: GUI framework and 3D graphics
- **Recursive Algorithms**: Backtracking and optimization
- **Multi-threading**: Concurrent processing for game logic
- **3D Graphics**: Advanced spatial visualization
- **File I/O**: CSV-based configuration and data persistence

---

## 🚀 Quick Start

### Prerequisites

1. **Java Development Kit**: Version 17 or higher
2. **JavaFX SDK**: Version 21.0.1 (included in project)
3. **Graphics Support**: 3D acceleration for Phase 3

### Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yusufserhatozkan/Pentomino-Algorithms.git
   cd Pentomino-Algorithms
   ```

2. **Configure JavaFX:**
   ```bash
   # Add to VM arguments when running
   --module-path javafx-sdk-21.0.1/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics
   ```

3. **Run individual phases:**
   - **Phase 1**: `java src/Phase1/SearchRecrusion.java`
   - **Phase 2**: `java src/Phase2/StartingPage.java`
   - **Phase 3**: `java src/Phase3/StartingPage.java`

### IDE Setup (Recommended)

1. Import project into IntelliJ IDEA, Eclipse, or VS Code
2. Configure JavaFX SDK in project settings
3. Add JavaFX modules to run configurations
4. Execute main classes for each phase

---

## 📁 Project Structure

```
├── src/
│   ├── Phase1/                 # Pentomino Solver
│   │   ├── PentominoBuilder.java
│   │   ├── SearchRecrusion.java
│   │   ├── SearchBruteForce.java
│   │   └── UI.java
│   ├── Phase2/                 # Tetris Game
│   │   ├── StartingPage.java
│   │   ├── HumanGame.java
│   │   ├── Bot.java
│   │   └── TetrisGameArea.java
│   └── Phase3/                 # 3D Knapsack
│       ├── StartingPage.java
│       ├── CargoSpace.java
│       ├── CargoSpaceVisualizer.java
│       └── RecursionLau.java
├── javafx-sdk-21.0.1/         # JavaFX Runtime
├── scores.csv                  # Game scores data
└── LICENSE
```

---

## 🎮 Usage Examples

### Phase 1: Solving a Pentomino Puzzle
```
1. Run SearchRecrusion.java
2. Input rectangle dimensions (e.g., 10x6)
3. Select pentomino pieces to use
4. Watch the solution visualization
```

### Phase 2: Playing Tetris
```
1. Run StartingPage.java
2. Choose game mode (Human/Bot/Best Sequence)
3. Use keyboard controls (WASD or arrows)
4. Achieve high scores and view statistics
```

### Phase 3: 3D Cargo Optimization
```
1. Run StartingPage.java
2. Watch automated 3D packing process
3. Use mouse to navigate 3D view
4. Observe optimal space utilization
```

---

## 🔧 Customization

Each phase offers extensive customization options:

- **Algorithm Parameters**: Modify search strategies and heuristics
- **Visualization Settings**: Adjust colors, animations, and rendering
- **Game Mechanics**: Configure speed, scoring, and difficulty
- **3D Graphics**: Customize camera, lighting, and materials

Detailed customization instructions are available in each phase's README.

---

## 🏆 Achievements

- **Algorithmic Excellence**: Implements multiple optimization strategies
- **User Experience**: Intuitive interfaces with real-time feedback
- **Performance**: Optimized algorithms for responsive gameplay
- **3D Innovation**: Advanced spatial visualization capabilities
- **Educational Value**: Demonstrates practical algorithm applications

---

## 📚 Learning Outcomes

This project demonstrates proficiency in:

- **Algorithm Design**: Recursive backtracking, optimization heuristics
- **Software Architecture**: Modular design, separation of concerns
- **User Interface Development**: JavaFX, event handling, 3D graphics
- **Game Development**: Real-time systems, AI implementation
- **Problem Solving**: Geometric constraints, spatial optimization

---

## 👥 Team Members

- **Hossam Mohamed Gohar**
- **Laurin Gschwenter**
- **Julia Całuch**
- **Yusuf Serhat Özkan**
- **Alexia Raportaru**
- **Cristina Stroescu**
- **Bruno Torrijo Marco**

**Academic Year**: 2023  
**Institution**: University Group Project  
**Completion**: December 2023

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

This is an academic project, but we welcome feedback and suggestions. Feel free to:

- Report issues or bugs
- Suggest improvements
- Share your own implementations
- Use this project for educational purposes


**Thank you for exploring our Pentomino Algorithms Project!** 🎯