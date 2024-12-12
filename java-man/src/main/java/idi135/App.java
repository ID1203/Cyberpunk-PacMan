// package idi135;


// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.io.IOException;

// import javax.swing.BorderFactory;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.border.Border;

// import java.awt.Dimension;
// import java.awt.ScrollPane;


// public class App 
// {
//      public static void main( String[] args )
//     {

//         try {
//             FirebaseService.initializeFirebase();
//         } catch (IOException e) {
//             e.printStackTrace();
//             return;
//         }


//         int rowCount = 23;
//         int columnCount = 19;
//         int tileSize = 32;
//         int boardWidth = columnCount * tileSize;
//         int boardHeight = rowCount * tileSize;

        
//         JFrame frame = new JFrame("Pac Man");
//         //frame.setVisible(true); 
//         frame.setLayout(new BorderLayout());
//         frame.setSize(boardWidth, boardHeight);
//         frame.setLocationRelativeTo(null);
//         frame.setResizable(false);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         ScorePanel scorePanel = new ScorePanel();
//         scorePanel.setPreferredSize(new Dimension(boardWidth, 30));
//         frame.add(scorePanel, BorderLayout.NORTH);

//         PacMan pacmanGame = new PacMan(scorePanel);
//         frame.add(pacmanGame);
//         frame.pack();
//         pacmanGame.requestFocus();
//         frame.setVisible(true);


//     }
// }

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
        frame.setSize(frameWidth, frameHeight);  // Updated to fit the border
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScorePanel scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(boardWidth, 30)); 

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

