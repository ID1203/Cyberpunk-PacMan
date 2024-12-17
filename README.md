# Cyberpunk PacMan

## Overview
Cyberpunk PacMan is a Java-based game that brings a modern twist to the classic PacMan. Featuring multiple levels, custom ghost AI, Firebase integration for a dynamic leaderboard, and retro-inspired graphics, this game is both engaging and nostalgic. Built with `Swing`, it delivers a simple yet interactive gaming experience.

## Features
- **Multiple Levels:** Three unique levels, each with custom-designed maps.
- **Dynamic Ghost AI:** Ghosts move unpredictably and interact with PacMan using collision detection.
- **Power-ups and Foods:** Score points by eating foods and progressing through levels.
- **Leaderboard Integration:** Stores player scores in Firebase and displays the top five scores.
- **Custom Sound Effects:** Retro sounds enhance the gameplay.
- **Game Over Screen:** Includes a prompt for username input and displays a leaderboard.

## Technologies Used
- **Java (Swing):** For building the game UI and handling events.
- **Firebase Realtime Database:** For storing and retrieving leaderboard data.
- **Java AWT:** For rendering game components and images.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/cyberpunk-pacman.git
   ```
2. Install the necessary dependencies, including Firebase SDK.
3. Configure Firebase:
   - Create a Firebase project and add the necessary credentials.
   - Update the `FirebaseService` class with your Firebase project details.
4. Run the game:
   ```bash
   javac idi135/App.java
   java idi135.App
   ```

## Game Instructions
1. Use the **arrow keys** to navigate PacMan:
   - `UP` for moving up.
   - `DOWN` for moving down.
   - `LEFT` for moving left.
   - `RIGHT` for moving right.
2. **Objective:**
   - Collect all foods to advance to the next level.
   - Avoid ghosts to prevent losing lives.
   - Score as many points as possible.
3. **Lives:**
   - PacMan starts with 3 lives.
   - Losing all lives ends the game.
4. **Game Over:**
   - Enter your username and submit your score to the leaderboard.
   - Check the leaderboard to see the top scores.

## Key Classes
- **PacMan:**
  - Handles game logic, including movement, collisions, and rendering.
- **Block:**
  - Represents game objects like walls, ghosts, foods, and PacMan.
- **App:**
  - The main entry point of the game, initializes the UI and game components.
- **ScorePanel and LivesPanel:**
  - Display the score and remaining lives during gameplay.
- **GameOverScreen:**
  - Renders the Game Over prompt and handles leaderboard interactions.
- **FirebaseService:**
  - Manages Firebase integration for saving and retrieving leaderboard data.

## Maps
Each level is represented as a string array where:
- `X`: Wall
- `P`: PacMan's starting position
- `b`, `o`, `p`, `r`: Ghosts (Blue, Orange, Pink, Red)
- ` `: Food

Example of Level 1:
```
"XXXXXXXXXXXXXXXXXXX",
"X        X        X",
"X XX XXX X XXX XX X",
"X                 X",
...
```

## Screenshots
_Include screenshots of gameplay and leaderboard to showcase the UI._


## Acknowledgments
- Inspired by the classic PacMan game.
- Special thanks to Firebase for seamless cloud integration.