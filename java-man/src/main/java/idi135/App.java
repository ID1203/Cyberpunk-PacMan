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

        int rowCount = 23; 
        int columnCount = 19; 
        int tileSize = 32; 
        int boardWidth = columnCount * tileSize; 
        int boardHeight = rowCount * tileSize; 
        int frameWidth = boardWidth + 40;  
        int frameHeight = boardHeight + 100; 

      
        JFrame frame = new JFrame("Cyberpunk PacMan");
        frame.setLayout(new BorderLayout()); 
        frame.setSize(frameWidth, frameHeight); 
        frame.setLocationRelativeTo(null); 
        frame.setResizable(false); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

       
        ScorePanel scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(boardWidth, 40));

        LivesPanel livesPanel = new LivesPanel(3);

        PacMan pacmanGame = new PacMan(scorePanel, livesPanel);
        pacmanGame.setPreferredSize(new Dimension(boardWidth, boardHeight)); 

        
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout()); 
        containerPanel.setPreferredSize(new Dimension(frameWidth, frameHeight)); 
        containerPanel.add(scorePanel, BorderLayout.NORTH); 
        containerPanel.add(pacmanGame, BorderLayout.CENTER); 
        containerPanel.add(livesPanel, BorderLayout.SOUTH); 
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20)); 

       
        frame.add(containerPanel);
        frame.pack(); 
        pacmanGame.requestFocus(); 
        frame.setVisible(true); 

        
    }
}

