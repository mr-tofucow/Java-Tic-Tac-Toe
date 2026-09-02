# Tic-Tac-Toe

A simple Java Swing implementation of the classic Tic-Tac-Toe game for two players.

## Overview

This project creates a 3x3 game board where players alternate turns as X and O. The game tracks turns, detects wins and ties, and updates the status text at the bottom of the window.

## Features

- 3x3 grid-based Tic-Tac-Toe gameplay
- Two-player turn system
- Win detection for rows, columns, and diagonals
- Tie detection when the board is full
- GUI status updates such as "X's Turn" and "Game over...O wins!"
- Menu options for starting a new game and quitting
- Keyboard shortcuts:
  - Ctrl/Cmd + R for a new game
  - Ctrl/Cmd + Q to quit

## How to Run

From the folder containing the Java source file, compile and run:

```bash
javac TicTacToe.java
java TicTacToe
```

## Game Controls

- Click any empty square to place your mark.
- Use the Options menu to:
  - start a new game
  - exit the application

## File Structure

- `TicTacToe.java` — main game logic and Swing GUI
- `README.md` — project documentation

## Notes

This game is implemented as a single class using Java Swing components such as `JFrame`, `JButton`, and `JMenuItem`.

## Author

Kyle Giang
