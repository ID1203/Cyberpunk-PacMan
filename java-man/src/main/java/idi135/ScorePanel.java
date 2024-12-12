package idi135;
import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private JLabel scoreLabel;
    private JLabel levelLabel;
    // private JLabel livesLabel;

    public ScorePanel(){

        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");
        // livesLabel = new JLabel("Lives: 1");

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(scoreLabel);
        add(levelLabel);
        // add(livesLabel);

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // livesLabel.setFont(new Font("Arial", Font.BOLD, 16));

        setBackground(new Color(0, 0, 0));

        scoreLabel.setForeground(Color.YELLOW);
        levelLabel.setForeground(Color.YELLOW);
        // livesLabel.setForeground(Color.YELLOW);

        setBorder(BorderFactory.createEmptyBorder(3, 200, 5, 20));
        

    }
    
    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }

    public void updateLevel(int level){
        levelLabel.setText("Level: " + level);
    }


}