# Morse Code Viewer

A JavaFX desktop application for encoding and decoding Morse code with an interactive visual tree representation and hands-on Morse pad simulator.

## Features

- **Text ⟷ Morse Code Translation**: Convert between plain text and Morse code
- **Interactive Morse Pad**: Practice Morse code by pressing and holding a button (short press = dot, long press = dash)
- **Visual Tree Representation**: See the binary tree structure used for Morse code encoding/decoding

## Technology Stack

- **Java 20**: Modern Java with latest language features
- **JavaFX 17.0.6**: Cross-platform GUI framework
- **Maven**: Build automation and dependency management
- **Custom Data Structures**: Binary tree implementation for efficient Morse code operations

## Getting Started

### Prerequisites

- Java 20 or higher
- Maven 3.6+
- JavaFX runtime (included as dependency)

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd morse
   ```

2. Build the project:
   ```bash
   ./mvnw clean compile
   ```

3. Run the application:
   ```bash
   ./mvnw javafx:run
   ```

### Alternative Run Method

You can also use Maven directly:
```bash
mvn clean javafx:run
```

## Project Structure

```
src/main/java/com/example/morse/
├── MorseApplication.java       # Main JavaFX application class
├── MorseController.java        # UI controller with event handlers
├── structure/
│   ├── MorseTree.java          # Binary tree for Morse code operations
│   └── Node.java               # Tree node implementation
└── utils/
    └── MorseCode.java          # Enum with standard Morse code mappings

src/main/resources/com/example/morse/
└── app-view.fxml               # JavaFX UI layout definition
```

## How It Works

### Binary Tree Structure

The application uses a custom binary tree where:
- **Left child** represents a dot (.)
- **Right child** represents a dash (-)
- **Tree traversal** from root to leaf gives the Morse code sequence
- **Leaf nodes** contain the corresponding letters

### Morse Code Mapping

Standard International Morse Code is supported for letters A-Z:

| Letter | Code | Letter | Code | Letter | Code |
|--------|------|--------|------|--------|------|
| A      | .-   | J      | .--- | S      | ...  |
| B      | -... | K      | -.-  | T      | -    |
| C      | -.-. | L      | .-.. | U      | ..-  |
| D      | -..  | M      | --   | V      | ...- |
| E      | .    | N      | -.   | W      | .--  |
| F      | ..-. | O      | ---  | X      | -..- |
| G      | --.  | P      | .--. | Y      | -.-- |
| H      | .... | Q      | --.- | Z      | --.. |
| I      | ..   | R      | .-.  |        |      |

## Usage

### Morse Pad Mode
1. Press and quickly release the red button for a **dot** (.)
2. Press and hold the red button (>200ms) for a **dash** (-)
3. Use "Enter" to add spaces between letters
4. Use "Clear" to reset input

### Text Conversion Mode
1. Enter text in the input area
2. Click "To Morse" to convert text → Morse code
3. Click "To Text" to convert Morse code → text
4. Results appear in the output area

### Tree Visualization
The tree viewer automatically displays the binary tree structure used for encoding/decoding, helping users understand how Morse code works algorithmically.
