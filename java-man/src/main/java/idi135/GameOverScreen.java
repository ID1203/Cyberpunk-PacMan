package idi135;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {

    private Image gameOverImage;

    public GameOverScreen() {
        setBackground(new Color(0, 0, 0, 0)); 
        loadGameOverImage(); 
    }

    // Load the "GAME OVER" image from resources
    private void loadGameOverImage() {
        // Use the same path you use to load other images
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("gameover2.png"));
        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Image loading failed!");
        } else {
            gameOverImage = icon.getImage();
      
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOverImage != null) {
            // Center the image on the screen
            int x = 50;
            int y = 100;
            g.drawImage(gameOverImage, x, y, null);
        }
    }
}



