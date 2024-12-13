package idi135;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BorderFactory;

public class App {
    public static void main(String[] args) {
        // Try to connect to Firebase for leaderboard. If it fails, catch IOException and print the error
        try {
            FirebaseService.initializeFirebase();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Initialize variables for the game board: rows, columns, and tile size
        int rowCount = 23; 
        int columnCount = 19; 
        int tileSize = 32; 
        int boardWidth = columnCount * tileSize; 
        int boardHeight = rowCount * tileSize; 
        int frameWidth = boardWidth + 40;  
        int frameHeight = boardHeight + 100; 

        // Create JFrame for the game window. Set title, size, and other default properties
        JFrame frame = new JFrame("Cyberpunk PacMan");
        frame.setLayout(new BorderLayout()); // Use BorderLayout for organizing components
        frame.setSize(frameWidth, frameHeight); 
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setResizable(false); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when the window is closed

        // Initialize the score panel e
        ScorePanel scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(boardWidth, 40)); // Place it at the top

        // Initialize the lives panel 
        LivesPanel livesPanel = new LivesPanel(3);

        // Create the main game area and pass the score and lives panels to the game constructor
        PacMan pacmanGame = new PacMan(scorePanel, livesPanel);
        pacmanGame.setPreferredSize(new Dimension(boardWidth, boardHeight)); // Match the board size

        // Create a container panel to hold the score panel, game area, and lives panel together
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout()); // Use BorderLayout for easy organization
        containerPanel.setPreferredSize(new Dimension(frameWidth, frameHeight)); 
        containerPanel.add(scorePanel, BorderLayout.NORTH); 
        containerPanel.add(pacmanGame, BorderLayout.CENTER); 
        containerPanel.add(livesPanel, BorderLayout.SOUTH); 
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20)); 

        // Add the container panel to the main frame
        frame.add(containerPanel);
        frame.pack(); // Adjust the frame size to fit its components
        pacmanGame.requestFocus(); // Set focus to the game area for keyboard input
        frame.setVisible(true); 
    }
}

