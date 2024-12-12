package idi135;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LivesPanel extends JPanel{

    private ArrayList<ImageIcon> livesIcons;
    private ImageIcon pacmanIcon;
    private int pacmanIconSize = 32;  // Default size

    public LivesPanel(int lives) {
        this.livesIcons = new ArrayList<>();
        this.pacmanIcon = new ImageIcon(getClass().getResource("/pacmanRight.png"));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(200, 50)); 

        
        updateLives(lives);
    }

    
    public void updateLives(int lives) {
        livesIcons.clear();
        for (int i = 0; i < lives; i++) {
            ImageIcon resizedPacman = resizeIcon(pacmanIcon, pacmanIconSize);
            livesIcons.add(resizedPacman);
        }
        repaint();
    }

   
    private ImageIcon resizeIcon(ImageIcon icon, int size) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public void increasePacmanSize() {
        pacmanIconSize += 8; 
        repaint();  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xOffset = 0;

        for (ImageIcon icon : livesIcons) {
            g.drawImage(icon.getImage(), xOffset, 0, null);
            xOffset += icon.getIconWidth() + 10; 
        }
    }
}
