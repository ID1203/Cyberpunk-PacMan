package idi135;


import java.io.IOException;
import javax.swing.JFrame;


public class App 
{
     public static void main( String[] args )
    {

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

        JFrame frame = new JFrame("Pac Man");
        //frame.setVisible(true); 
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);


    }
}
